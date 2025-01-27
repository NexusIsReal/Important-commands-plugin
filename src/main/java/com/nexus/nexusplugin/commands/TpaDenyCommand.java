package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import com.nexus.nexusplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaDenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.tpa")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.getMessage("player-only"));
            return true;
        }

        Player player = (Player) sender;
        TpaCommand.TpaRequest request = TpaCommand.getRequest(player.getUniqueId());

        if (request == null) {
            player.sendMessage(MessageUtils.getMessage("tpa-no-request"));
            return true;
        }

        Player requester = Bukkit.getPlayer(request.getSender());
        TpaCommand.removeRequest(player.getUniqueId());

        if (requester != null && requester.isOnline()) {
            CommandUtils.notifyPlayers(player, requester,
                "tpa-denied-target",
                "tpa-denied",
                "%player%", player.getName());
        } else {
            player.sendMessage(MessageUtils.getMessage("tpa-denied-offline"));
        }

        return true;
    }
} 