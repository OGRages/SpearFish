package me.rages.spearfish;

import me.rages.spearfish.command.SpearCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpearFishPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // initialize spear command
        SpearCommand.initialize("fishingspear", this);

    }
}
