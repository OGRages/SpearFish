package me.rages.spearfishing.listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashSet;
import java.util.Set;

public class FishingListener implements Listener {

    private Set<Projectile> cachedProjectiles = new HashSet<>();

    @EventHandler
    public void onSpearLaunch(PlayerLaunchProjectileEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        cachedProjectiles.add(
                player.launchProjectile(event.getProjectile().getClass(), event.getProjectile().getVelocity())
        );
    }

    @EventHandler
    public void onCollide(ProjectileHitEvent event) {

    }

    @EventHandler
    public void onSpearHit(ProjectileHitEvent event) {
        // check if projectile has hit an entity
        if (cachedProjectiles.contains(event.getEntity())) {
            if (event.getHitEntity() != null) {
                Entity entity = event.getHitEntity();
                if (entity instanceof Fish) {
                    // check if fish entity is a can be speared
                    Bukkit.broadcastMessage("killed fish");
                    entity.remove();
                }
            }
            event.getEntity().remove();
        }
    }


}
