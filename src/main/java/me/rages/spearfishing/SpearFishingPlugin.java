package me.rages.spearfishing;

import me.rages.spearfishing.command.FishingCommand;
import me.rages.spearfishing.listeners.FishingListener;
import me.rages.spearfishing.utils.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class SpearFishingPlugin extends JavaPlugin {

    private static final ItemStack SPEAR_ITEM = new ItemStack(Material.TRIDENT);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // initialize spear command
        FishingCommand.initialize("fishingspear", this);
        getServer().getPluginManager().registerEvents(new FishingListener(), this);

    }

    @Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();
        ItemMeta itemMeta = SPEAR_ITEM.getItemMeta();
        itemMeta.setDisplayName(Color.colorize(getConfig().getString("spear-itemstack.name")));
        List<String> configLore = getConfig().getStringList("spear-itemstack.lore")
                .stream().map(Color::colorize)
                .collect(Collectors.toList());
        itemMeta.setLore(configLore);
        SPEAR_ITEM.setItemMeta(itemMeta);
    }

    public static ItemStack getSpearItem() {
        return SPEAR_ITEM;
    }
}
