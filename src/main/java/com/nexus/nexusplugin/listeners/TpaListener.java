package com.nexus.nexusplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.nexus.nexusplugin.managers.TpaManager;

public class TpaListener implements Listener {
    private final TpaManager tpaManager;

    public TpaListener(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        tpaManager.removeRequest(event.getPlayer().getUniqueId());
    }
} 