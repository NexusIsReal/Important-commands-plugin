package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import com.nexus.nexusplugin.managers.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {
    private final InventoryManager inventoryManager;

    public EnderChestCommand(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.enderchest")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.getMessage("player-only"));
            return true;
        }

        Player player = (Player) sender;
        Player target;

        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(MessageUtils.getMessage("player-not-found"));
                return true;
            }
        } else {
            target = player;
        }

        inventoryManager.openEnderChest(player, target);
        if (target != player) {
            player.sendMessage(MessageUtils.formatMessage("enderchest-other", "%player%", target.getName()));
        } else {
            player.sendMessage(MessageUtils.getMessage("enderchest-self"));
        }

        return true;
    }
} 