package com.nexus.nexusplugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.nexus.nexusplugin.commands.*;
import com.nexus.nexusplugin.listeners.*;

public class NexusPlugin extends JavaPlugin {
    private static NexusPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        GamemodeCommand gamemodeCommand = new GamemodeCommand();

        getCommand("gamemode").setExecutor(gamemodeCommand);
        getCommand("gamemode").setTabCompleter(gamemodeCommand);
        getCommand("god").setExecutor(new GodCommand());
        getCommand("openinv").setExecutor(new OpenInvCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaaccept").setExecutor(new TpaAcceptCommand());
        getCommand("tpadeny").setExecutor(new TpaDenyCommand());
        getCommand("trash").setExecutor(new TrashCommand());
    }

    private void registerListeners() {
        var pm = getServer().getPluginManager();
        pm.registerEvents(new TrashListener(), this);
        pm.registerEvents(new GodListener(), this);
        pm.registerEvents(new TpaListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
    }

    public static NexusPlugin getInstance() {
        return instance;
    }
} 