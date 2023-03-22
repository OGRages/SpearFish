package me.rages.spearfishing.listeners;

import me.rages.spearfishing.SpearFishingPlugin;
import me.rages.spearfishing.data.SpearableFish;
import me.rages.spearfishing.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class FishingSpawnerListener implements Listener {

    public SpearFishingPlugin plugin;

    public FishingSpawnerListener(SpearFishingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawnerSpawnEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER && event.getEntity().getType() == EntityType.TROPICAL_FISH) {
            event.setCancelled(true);
            SpearableFish spearableFish = getRandomFish();
            if (spearableFish != null) {
                Bukkit.broadcastMessage("spawning a " + spearableFish.getName());
                spawnSpearableFish(spearableFish, event.getLocation());
            } else {
                Bukkit.broadcastMessage("could not find a fish to spawn");
            }
        }
    }

    private SpearableFish getRandomFish() {
        double num = ThreadLocalRandom.current().nextInt(1000) / 10f;
        for (Map.Entry<SpearableFish, Double> entry : plugin.getSpawnableFishes().entrySet()) {
            if ((num = num - entry.getValue()) < 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void spawnSpearableFish(SpearableFish fish, Location location) {
        TropicalFish tropicalFish = (TropicalFish) location.getWorld().spawnEntity(location, EntityType.TROPICAL_FISH);
        tropicalFish.setCustomNameVisible(true);
        tropicalFish.setCustomName(Color.colorize(fish.getDisplayName()));
        tropicalFish.setPattern(fish.getPattern());
        tropicalFish.setPatternColor(fish.getPatternColor());
        tropicalFish.setBodyColor(fish.getFishColor());
    }

}
