package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import com.nexus.nexusplugin.managers.TpaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {
    private final TpaManager tpaManager;

    public TpaCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

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

        if (args.length < 1) return false;

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(MessageUtils.getMessage("player-not-found"));
            return true;
        }

        if (target == player) {
            player.sendMessage(MessageUtils.getMessage("tpa-self"));
            return true;
        }

        if (tpaManager.hasPendingRequest(target.getUniqueId())) {
            player.sendMessage(MessageUtils.getMessage("tpa-already-pending"));
            return true;
        }

        tpaManager.createRequest(player, target);
        target.sendMessage(MessageUtils.formatMessage("tpa-received", "%player%", player.getName()));
        player.sendMessage(MessageUtils.formatMessage("tpa-sent", "%player%", target.getName()));

        return true;
    }
} 