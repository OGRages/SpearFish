package me.rages.spearfishing.utils;

import org.bukkit.ChatColor;

public class Color {

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
