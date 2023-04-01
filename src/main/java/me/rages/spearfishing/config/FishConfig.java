package me.rages.spearfishing.config;

import me.rages.spearfishing.SpearFishingPlugin;
import me.rages.spearfishing.data.SpearableFish;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FishConfig {

    private SpearFishingPlugin plugin;

    private FishConfig(SpearFishingPlugin plugin) {
        this.plugin = plugin;
        File configFile = new File(plugin.getDataFolder(), "fish.yml");

        if (!configFile.exists()) {
            // save file from resources
            InputStream inputStream = plugin.getResource("fish.yml");
            // copy the input stream to the target file
            try {
                FileOutputStream outputStream = new FileOutputStream(configFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        ConfigurationSection fishSection = config.getConfigurationSection("fish");

        // create a new Fish object using the Fish.load() method
        fishSection.getKeys(false).forEach(fishName -> {
            SpearableFish fish = SpearableFish.load(config, fishName);
            plugin.getSpawnableFishes().put(fish, fish.getSpawnChance());
            plugin.getSpearableFishMap().put(fish.getName(), fish);
        });
    }

    public static FishConfig loadConfig(SpearFishingPlugin plugin) {
        return new FishConfig(plugin);
    }

}
