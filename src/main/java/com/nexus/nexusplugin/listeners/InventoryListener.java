package com.nexus.nexusplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        
        String title = event.getView().getTitle();
        if (title.endsWith("'s Inventory") || title.endsWith("'s Enderchest")) {
            Player viewer = (Player) event.getWhoClicked();
            if (!viewer.hasPermission("nexusplugin.openinv.edit") && 
                !viewer.hasPermission("nexusplugin.enderchest.edit")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (Player viewer : player.getServer().getOnlinePlayers()) {
            if (viewer.getOpenInventory().getTitle().contains(player.getName())) {
                viewer.closeInventory();
            }
        }
    }
} 