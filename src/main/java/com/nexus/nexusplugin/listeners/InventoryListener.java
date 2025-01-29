package com.nexus.nexusplugin.listeners;

import com.nexus.nexusplugin.managers.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryListener implements Listener {
    private final InventoryManager inventoryManager;

    public InventoryListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (inventoryManager.isTrashInventory(event.getInventory())) {
            if (event.getClickedInventory() != null && 
                event.getClickedInventory().getType() == InventoryType.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (inventoryManager.isTrashInventory(event.getInventory())) {
            if (event.getInventory().getType() == InventoryType.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        inventoryManager.removeTrashInventory(event.getPlayer().getUniqueId());
    }
} 