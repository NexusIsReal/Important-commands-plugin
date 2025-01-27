package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class FixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.fix")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.getMessage("player-only"));
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType().isAir()) {
            player.sendMessage(MessageUtils.getMessage("no-item"));
            return true;
        }

        if (item.getItemMeta() instanceof Damageable meta) {
            meta.setDamage(0);
            item.setItemMeta(meta);
            player.sendMessage(MessageUtils.getMessage("item-fixed"));
        } else {
            player.sendMessage(MessageUtils.getMessage("no-item"));
        }

        return true;
    }
} 