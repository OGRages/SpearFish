package me.rages.spearfishing.command;

import com.google.common.collect.ImmutableMap;
import me.rages.spearfishing.SpearFishingPlugin;
import me.rages.spearfishing.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FishingCommand implements CommandExecutor, TabCompleter {

    private SpearFishingPlugin plugin;
    private String command;

    private final static ImmutableMap<String, SubCommand> subCommands = ImmutableMap.of(

            "help", new SubCommand() {
                private static final String LINE = "&f/fishingspear %s &b- &7%s";

                @Override
                public void execute(CommandSender sender, String[] args) {
                    subCommands.entrySet().stream()
                            .map(cmd -> String.format(Color.colorize(LINE), cmd.getKey().toLowerCase(), cmd.getValue().getDescription()))
                            .forEach(sender::sendMessage);
                }
            }.desc("displays help menu"),

            "spear", new SubCommand("spearfishing.command.give") {
                @Override
                public void execute(CommandSender sender, String[] args) {
                    Bukkit.broadcastMessage("spear command");
                }
            }.desc("give a player a spear"),

            "sell", new SubCommand("spearfishing.command.sell") {
                @Override
                public void execute(CommandSender sender, String[] args) {
                    Bukkit.broadcastMessage("sell command");
                }
            }.desc("sell all of your fish")
    );

    public static FishingCommand initialize(String command, SpearFishingPlugin plugin) {
        return new FishingCommand(command, plugin);
    }

    private FishingCommand(String command, SpearFishingPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());

        if (args.length == 0 || subCommand == null) {
            subCommands.get("help").execute(commandSender, args);
            return true;
        } else {
            subCommand.execute(commandSender, args);
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
