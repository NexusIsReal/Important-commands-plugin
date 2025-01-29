package com.nexus.nexusplugin.listeners;

import com.nexus.nexusplugin.managers.GodManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final GodManager godManager;

    public PlayerDeathListener(GodManager godManager) {
        this.godManager = godManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (godManager.isInGodMode(player.getUniqueId())) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
} 