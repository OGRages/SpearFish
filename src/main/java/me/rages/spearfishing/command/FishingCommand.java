package me.rages.spearfishing.command;

import com.google.common.collect.ImmutableMap;
import me.rages.spearfishing.SpearFishingPlugin;
import me.rages.spearfishing.command.impl.EditCommand;
import me.rages.spearfishing.utils.Color;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FishingCommand implements CommandExecutor, TabCompleter {

    private ImmutableMap<String, SubCommand> subCommands;
    private SpearFishingPlugin plugin;


    public static FishingCommand initialize(String command, SpearFishingPlugin plugin) {
        return new FishingCommand(command, plugin);
    }

    private FishingCommand(String command, SpearFishingPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);

        this.subCommands = ImmutableMap.of(
                "help", new SubCommand() {

                    private static final String HEADER = "     &8--- &c&lSPEAR FISHING &8---     ";
                    private static final String LINE = "&f/spearfishing %s &c- &7%s";

                    @Override
                    public void execute(CommandSender sender, String[] args) {
                        sender.sendMessage(Color.colorize(HEADER));
                        subCommands.entrySet().stream()
                                .map(cmd -> String.format(Color.colorize(LINE), cmd.getKey().toLowerCase(), cmd.getValue().getDescription()))
                                .forEach(sender::sendMessage);
                    }
                }.desc("displays help menu"),

                "give", new SubCommand("spearfishing.command.give") {
                    @Override
                    public void execute(CommandSender sender, String[] args) {

                        if (args.length < 2) {
                            sender.sendMessage(ChatColor.RED + "/fishingspear give [player] <amount>");
                            return;
                        }

                        Player player = Bukkit.getServer().getPlayer(args[1]);

                        if (player == null) {
                            sender.sendMessage(ChatColor.RED + "The player '" + args[1] + "' is not online.");
                            return;
                        }

                        int amount = NumberUtils.toInt(args[2], 1); // parse or default 1

                        for (int i = 0; i < amount; i++) {
                            player.getInventory().addItem(SpearFishingPlugin.getSpearItem());
                        }
                    }

                }.desc("give a player a spear"),

                "sell", new SubCommand("spearfishing.command.sell") {
                    @Override
                    public void execute(CommandSender sender, String[] args) {

                        Player player = (Player) sender;
                        Arrays.stream(player.getInventory().getContents())
                                .filter(itemStack -> itemStack != null && itemStack.hasItemMeta())
                                .forEach(itemStack -> {
                                    if (itemStack.getItemMeta().getPersistentDataContainer().has(plugin.getCustomFishKey())) {
                                        String customFishData = itemStack.getItemMeta().getPersistentDataContainer().get(plugin.getCustomFishKey(), PersistentDataType.STRING);
                                        for (int i = 0; i < itemStack.getAmount(); i++) {
                                            plugin.getSpearableFishMap().get(customFishData).getRewards()
                                                    .forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd));

                                        }
                                        player.getInventory().removeItem(itemStack);
                                    }
                                });
                    }

                }.desc("sell all of your fish"),

                "edit", new EditCommand(plugin).desc("edit fish spawner blocks")
        );

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        SubCommand helpCommand = subCommands.get("help");

        if (args.length == 0) {
            helpCommand.execute(commandSender, args);
        } else if (!subCommands.containsKey(args[0].toLowerCase())) {
            helpCommand.execute(commandSender, args);
        } else {
            subCommands.get(args[0].toLowerCase()).execute(commandSender, args);
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
