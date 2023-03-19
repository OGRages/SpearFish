package me.rages.spearfishing.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class FishingListener implements Listener {

    @EventHandler
    public void onSpearLaunch(ProjectileLaunchEvent event) {

    }

    @EventHandler
    public void onSpearHit(ProjectileHitEvent event) {
        // check if projectile has hit an entity
        if (event.getHitEntity() != null) {
            Entity entity = event.getHitEntity();
            if (entity instanceof Fish) {
                // check if fish entity is a can be speared

            }
        }
    }


}
