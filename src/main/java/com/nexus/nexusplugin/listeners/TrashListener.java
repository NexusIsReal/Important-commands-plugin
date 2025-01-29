package com.nexus.nexusplugin.listeners;

import com.nexus.nexusplugin.managers.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrashListener implements Listener {
    private final InventoryManager inventoryManager;

    public TrashListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (inventoryManager.isTrashInventory(event.getInventory())) {
            event.getInventory().clear();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        inventoryManager.removeTrashInventory(event.getPlayer().getUniqueId());
    }
} 