package me.rages.spearfishing.listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import me.rages.spearfishing.SpearFishingPlugin;
import me.rages.spearfishing.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class FishingSpearListener implements Listener {

    private final SpearFishingPlugin plugin;

    public FishingSpearListener(SpearFishingPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onSpearLaunch(PlayerLaunchProjectileEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        if (isSpearItem(event.getItemStack())) {
            Trident trident = (Trident) player.launchProjectile(event.getProjectile().getClass(), event.getProjectile().getVelocity());
            trident.setItem(SpearFishingPlugin.getSpearItem());
        }
    }

    @EventHandler
    public void onSpearHit(ProjectileHitEvent event) {
        // check if projectile has hit an entity

        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.TRIDENT && event.getEntity().getShooter() instanceof Player) {
            ItemStack itemStack = ((Trident) entity).getItem();
            if (isSpearItem(itemStack) && event.getHitEntity() != null) {
                Entity hitEntity = event.getHitEntity();
                if (hitEntity instanceof TropicalFish) {
                    TropicalFish fish = (TropicalFish) hitEntity;
                    // check if fish entity is a can be speared
                    String fishName = getFishName(fish);

                    Player player = (Player) event.getEntity().getShooter();

                    if (fishName != null) {
                        hitEntity.remove();
                        double num = ThreadLocalRandom.current().nextInt(1000) / 10f;
                        if (plugin.getSpearableFishMap().get(fishName).getCatchChance() > num) {
                            player.sendMessage(Color.colorize(
                                    plugin.getConfig().getString("messages.caught-fish").replace("%fish_name%", fishName)
                                )
                            );

                            @NotNull HashMap<Integer, ItemStack> leftOvers = player.getInventory().addItem(getFishItem(fishName));
                            if (!leftOvers.isEmpty()) {
                                leftOvers.values().forEach(item -> player.getLocation().getWorld().dropItem(player.getLocation(), item));
                            }

                        } else {
                            player.sendMessage(Color.colorize(
                                    plugin.getConfig().getString("messages.escaped-fish").replace("%fish_name%", fishName)
                                )
                            );
                        }
                    }
                }
            }
            event.getEntity().remove();
        }
    }

    private boolean isSpearItem(ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(plugin.getSpearItemKey());
    }

    private String getFishName(TropicalFish fish) {
        if (fish.getPersistentDataContainer().has(plugin.getCustomFishKey())) {
            return fish.getPersistentDataContainer().get(plugin.getCustomFishKey(), PersistentDataType.STRING);
        }
        return null;
    }

    public ItemStack getFishItem(String name) {
        ItemStack itemStack = new ItemStack(Material.valueOf(plugin.getConfig().getString("fish-item.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(
                Color.colorize(plugin.getConfig().getString("fish-item.name").replace("%display_name%", name))
        );
        List<String> configLore = plugin.getConfig().getStringList("fish-item.lore")
                .stream().map(Color::colorize)
                .collect(Collectors.toList());
        itemMeta.setLore(configLore);
        itemMeta.getPersistentDataContainer().set(plugin.getCustomFishKey(), PersistentDataType.STRING, name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
