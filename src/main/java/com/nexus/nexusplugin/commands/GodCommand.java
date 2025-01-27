package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import com.nexus.nexusplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.god")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        Player target;
        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(MessageUtils.getMessage("player-not-found"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MessageUtils.getMessage("player-only"));
                return true;
            }
            target = (Player) sender;
        }

        boolean godMode = !target.isInvulnerable();
        target.setInvulnerable(godMode);
        if (godMode) {
            target.setFoodLevel(20);
            target.setSaturation(20f);
        }

        CommandUtils.notifyPlayers(sender, target,
            godMode ? "god-enabled" : "god-disabled",
            godMode ? "god-enabled-other" : "god-disabled-other",
            "%player%", target.getName());

        return true;
    }
} 