package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import com.nexus.nexusplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {
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
        if (args.length > 0) {
            if (!player.hasPermission("nexusplugin.enderchest.others")) {
                player.sendMessage(MessageUtils.getMessage("no-permission"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(MessageUtils.getMessage("player-not-found"));
                return true;
            }

            player.openInventory(target.getEnderChest());
            CommandUtils.notifyPlayers(sender, target,
                "enderchest-opened-target",
                "enderchest-opened-other",
                "%player%", player.getName());
        } else {
            player.openInventory(player.getEnderChest());
            player.sendMessage(MessageUtils.getMessage("enderchest-opened"));
        }

        return true;
    }
} 