package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class TrashCommand implements CommandExecutor, Listener {
    private static final String TRASH_TITLE = "Trash";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.trash")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.getMessage("player-only"));
            return true;
        }

        Player player = (Player) sender;
        Inventory trash = Bukkit.createInventory(null, 54, TRASH_TITLE);
        player.openInventory(trash);
        player.sendMessage(MessageUtils.getMessage("trash-opened"));

        return true;
    }

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