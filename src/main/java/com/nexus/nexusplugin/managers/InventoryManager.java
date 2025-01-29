package com.nexus.nexusplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager {
    private final Map<UUID, Inventory> trashInventories = new HashMap<>();
    private final Plugin plugin;

    public InventoryManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void openPlayerInventory(Player viewer, Player target) {
        viewer.openInventory(target.getInventory());
    }

    public void openEnderChest(Player viewer, Player target) {
        viewer.openInventory(target.getEnderChest());
    }

    public void openTrashInventory(Player player) {
        Inventory trashInv = trashInventories.computeIfAbsent(player.getUniqueId(),
            k -> Bukkit.createInventory(null, 54, "Trash"));
        player.openInventory(trashInv);
    }

    public boolean isTrashInventory(Inventory inventory) {
        return trashInventories.containsValue(inventory);
    }

    public void removeTrashInventory(UUID playerUUID) {
        trashInventories.remove(playerUUID);
    }
} 