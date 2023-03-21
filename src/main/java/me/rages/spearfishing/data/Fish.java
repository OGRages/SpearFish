package me.rages.spearfishing.data;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.TropicalFish;

public class Fish {

    private String name;
    private String displayName;

    private TropicalFish.Pattern pattern;

    private DyeColor fishColor;
    private DyeColor patternColor;

    private double catchChance; // 1 -> 0.001
    private double spawnChance; // 1 -> 0.001

    private Fish(FileConfiguration config, String fishName) {
        // name data
        this.name = config.getString(String.format("fish.%s.name", fishName));
        this.displayName = config.getString(String.format("fish.%s.display-name", fishName));

        // fish data
        this.fishColor = config.getObject(String.format("fish.%s.color", fishName), DyeColor.class, DyeColor.RED);
        this.pattern = config.getObject(String.format("fish.%s.pattern-color", fishName), TropicalFish.Pattern.class, TropicalFish.Pattern.GLITTER);
        this.patternColor = config.getObject(String.format("fish.%s.pattern-color", fishName), DyeColor.class, DyeColor.ORANGE);

        // spawn data
        this.catchChance = config.getDouble(String.format("fish.%s.catch-chance", fishName));
        this.spawnChance = config.getDouble(String.format("fish.%s.spawn-chance", fishName));
        System.out.println(toString());
    }

    public static Fish load(FileConfiguration config, String fishName) {
        return new Fish(config, fishName);
    }

    @Override
    public String toString() {
        return "Fish{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", pattern=" + pattern +
                ", fishColor=" + fishColor +
                ", patternColor=" + patternColor +
                ", catchChance=" + catchChance +
                ", spawnChance=" + spawnChance +
                '}';
    }
}
