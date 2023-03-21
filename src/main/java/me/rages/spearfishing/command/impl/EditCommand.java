package me.rages.spearfishing.command.impl;

import me.rages.spearfishing.SpearFishingPlugin;
import me.rages.spearfishing.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EditCommand extends SubCommand implements Listener {

    private Set<UUID> players = new HashSet<>();

    private SpearFishingPlugin plugin;

    public EditCommand(SpearFishingPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
            return;
        }

        Player player = (Player) sender;

        if (players.contains(player.getUniqueId())) {
            players.remove(player.getUniqueId());
            sender.sendMessage(ChatColor.RED + "You have disabled spawner edit mode.");
        } else {
            players.add(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "Click on Tropical Fish Spawners to allow them to spawn spearable fish.");
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> players.remove(player.getUniqueId()), 20 * 120);
        }
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // cache block
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            // remove from cache
        }
    }

}
