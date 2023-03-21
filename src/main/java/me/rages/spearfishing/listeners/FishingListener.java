package me.rages.spearfishing.listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import me.rages.spearfishing.SpearFishingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class FishingListener implements Listener {

    private final SpearFishingPlugin plugin;

    public FishingListener(SpearFishingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpearLaunch(PlayerLaunchProjectileEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        if (isSpearItem(event.getItemStack())) {
            Trident trident = (Trident) player.launchProjectile(event.getProjectile().getClass(), event.getProjectile().getVelocity());
            trident.setItem(plugin.getSpearItem());
        }
    }

    @EventHandler
    public void onSpearHit(ProjectileHitEvent event) {
        // check if projectile has hit an entity
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.TRIDENT) {
            ItemStack itemStack = ((Trident) entity).getItem();
            if (isSpearItem(itemStack) && event.getHitEntity() != null) {
                Entity hitEntity = event.getHitEntity();
                if (hitEntity instanceof TropicalFish) {
                    // check if fish entity is a can be speared
                    Bukkit.broadcastMessage("caught fish");
                    hitEntity.remove();
                }
            }
            event.getEntity().remove();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawnerSpawnEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER && event.getEntity().getType() == EntityType.TROPICAL_FISH) {
            event.setCancelled(true);
            System.out.println("cancelled");
        }
    }

    private boolean isSpearItem(ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(plugin.getNamespacedKey());
    }

}
