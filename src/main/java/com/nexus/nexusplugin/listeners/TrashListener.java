package com.nexus.nexusplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

public class TrashListener implements Listener {
    private static final String TRASH_TITLE = "Trash";

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(TRASH_TITLE)) {
            event.getInventory().clear();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TRASH_TITLE)) {
            if (event.getClickedInventory() != null && 
                event.getClickedInventory().getType() == InventoryType.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals(TRASH_TITLE)) {
            if (event.getInventory().getType() == InventoryType.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }
} 