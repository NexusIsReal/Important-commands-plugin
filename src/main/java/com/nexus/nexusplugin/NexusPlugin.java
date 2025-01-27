package com.nexus.nexusplugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.nexus.nexusplugin.commands.*;

public class NexusPlugin extends JavaPlugin {
    private static NexusPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
    }

    private void registerCommands() {
        TrashCommand trashCommand = new TrashCommand();
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
        getCommand("trash").setExecutor(trashCommand);

        getServer().getPluginManager().registerEvents(trashCommand, this);
    }

    public static NexusPlugin getInstance() {
        return instance;
    }
} 