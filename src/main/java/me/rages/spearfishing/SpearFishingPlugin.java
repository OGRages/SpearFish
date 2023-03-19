package me.rages.spearfishing;

import me.rages.spearfishing.command.FishingCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpearFishingPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // initialize spear command
        FishingCommand.initialize("fishingspear", this);

    }
}
