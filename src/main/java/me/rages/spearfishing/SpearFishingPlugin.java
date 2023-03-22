package me.rages.spearfishing;

import me.rages.spearfishing.command.FishingCommand;
import me.rages.spearfishing.config.FishConfig;
import me.rages.spearfishing.data.SpearableFish;
import me.rages.spearfishing.listeners.FishingSpawnerListener;
import me.rages.spearfishing.listeners.FishingSpearListener;
import me.rages.spearfishing.utils.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SpearFishingPlugin extends JavaPlugin {

    private static final ItemStack SPEAR_ITEM = new ItemStack(Material.TRIDENT);

    private NamespacedKey NAMESPACED_KEY;

    private Map<SpearableFish, Double> spawnableFishes = new LinkedHashMap<>();


    @Override
    public void onEnable() {
        NAMESPACED_KEY = new NamespacedKey(this, "spearfishing");

        saveDefaultConfig();
        FishConfig.loadConfig(this);

        // initialize spear command
        FishingCommand.initialize("spearfishing", this);
        getServer().getPluginManager().registerEvents(new FishingSpearListener(this), this);
        getServer().getPluginManager().registerEvents(new FishingSpawnerListener(this), this);

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
        itemMeta.getPersistentDataContainer().set(NAMESPACED_KEY, PersistentDataType.SHORT, (short) 0);
        SPEAR_ITEM.setItemMeta(itemMeta);
    }

    public static ItemStack getSpearItem() {
        return SPEAR_ITEM;
    }

    public NamespacedKey getNamespacedKey() {
        return NAMESPACED_KEY;
    }

    public Map<SpearableFish, Double> getSpawnableFishes() {
        return spawnableFishes;
    }
}
