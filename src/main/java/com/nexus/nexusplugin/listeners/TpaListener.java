package com.nexus.nexusplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.nexus.nexusplugin.commands.TpaCommand;

public class TpaListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        TpaCommand.removeRequest(event.getPlayer().getUniqueId());
    }
} 