package me.rages.spearfishing.command;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    private String permission;
    private String description;

    public SubCommand() {}

    public SubCommand(String permission) {
        this.permission = permission;
    }

    public SubCommand desc(String description) {
        this.description = description;
        return this;
    }

    public abstract void execute(CommandSender sender, String[] args);


    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPlayerRequired() {
        return false;
    }
}