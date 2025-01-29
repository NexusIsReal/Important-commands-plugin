package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import com.nexus.nexusplugin.managers.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenInvCommand implements CommandExecutor {
    private final InventoryManager inventoryManager;

    public OpenInvCommand(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.openinv")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.getMessage("player-only"));
            return true;
        }

        if (args.length < 1) return false;

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(MessageUtils.getMessage("player-not-found"));
            return true;
        }

        if (target == player) {
            player.sendMessage(MessageUtils.getMessage("openinv-self"));
            return true;
        }

        inventoryManager.openPlayerInventory(player, target);
        player.sendMessage(MessageUtils.formatMessage("openinv-success", "%player%", target.getName()));

        return true;
    }
} 