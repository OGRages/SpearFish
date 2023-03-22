package me.rages.spearfishing.data;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.TropicalFish;

import java.util.List;

public class SpearableFish {

    private String name;
    private String displayName;

    private TropicalFish.Pattern pattern;

    private DyeColor fishColor;
    private DyeColor patternColor;

    // chances are supported from 0.01 to 100
    private double catchChance;
    private double spawnChance;

    private List<String> rewards;

    private SpearableFish(FileConfiguration config, String fishName) {
        // name data
        this.name = config.getString(String.format("fish.%s.name", fishName));
        this.displayName = config.getString(String.format("fish.%s.display-name", fishName));

        // fish data

        this.pattern = TropicalFish.Pattern.valueOf(config.getString(String.format("fish.%s.pattern", fishName), "GLITTER"));
        this.patternColor = DyeColor.valueOf(config.getString(String.format("fish.%s.pattern-color", fishName), "ORANGE"));
        this.fishColor = DyeColor.valueOf(config.getString(String.format("fish.%s.body-color", fishName), "RED"));

        // spawn data
        this.catchChance = config.getDouble(String.format("fish.%s.catch-chance", fishName));
        this.spawnChance = config.getDouble(String.format("fish.%s.spawn-chance", fishName));
        this.rewards = config.getStringList(String.format("fish.%s.reward-cmds", fishName));
    }

    public static SpearableFish load(FileConfiguration config, String fishName) {
        return new SpearableFish(config, fishName);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TropicalFish.Pattern getPattern() {
        return pattern;
    }

    public DyeColor getFishColor() {
        return fishColor;
    }

    public DyeColor getPatternColor() {
        return patternColor;
    }

    public double getCatchChance() {
        return catchChance;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public List<String> getRewards() {
        return rewards;
    }
}
