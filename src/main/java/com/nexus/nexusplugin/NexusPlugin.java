package com.nexus.nexusplugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.nexus.nexusplugin.commands.*;
import com.nexus.nexusplugin.listeners.*;
import com.nexus.nexusplugin.managers.*;

public class NexusPlugin extends JavaPlugin {
    private static NexusPlugin instance;
    private TpaManager tpaManager;
    private GodManager godManager;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        initializeManagers();
        registerCommands();
        registerListeners();
    }

    private void initializeManagers() {
        tpaManager = new TpaManager(this);
        godManager = new GodManager(this);
        inventoryManager = new InventoryManager(this);
    }

    private void registerCommands() {
        GamemodeCommand gamemodeCommand = new GamemodeCommand();

        getCommand("gamemode").setExecutor(gamemodeCommand);
        getCommand("gamemode").setTabCompleter(gamemodeCommand);
        getCommand("god").setExecutor(new GodCommand(godManager));
        getCommand("openinv").setExecutor(new OpenInvCommand(inventoryManager));
        getCommand("enderchest").setExecutor(new EnderChestCommand(inventoryManager));
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("tpa").setExecutor(new TpaCommand(tpaManager));
        getCommand("tpaaccept").setExecutor(new TpaAcceptCommand(tpaManager));
        getCommand("tpadeny").setExecutor(new TpaDenyCommand(tpaManager));
        getCommand("trash").setExecutor(new TrashCommand(inventoryManager));
    }

    private void registerListeners() {
        var pm = getServer().getPluginManager();
        pm.registerEvents(new TrashListener(inventoryManager), this);
        pm.registerEvents(new GodListener(godManager), this);
        pm.registerEvents(new TpaListener(tpaManager), this);
        pm.registerEvents(new InventoryListener(inventoryManager), this);
        pm.registerEvents(new PlayerDeathListener(godManager), this);
    }

    public static NexusPlugin getInstance() {
        return instance;
    }
} 