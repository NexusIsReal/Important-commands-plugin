package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaCommand implements CommandExecutor {
    private static final Map<UUID, TpaRequest> tpaRequests = new HashMap<>();

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

        if (hasPendingRequest(target.getUniqueId())) {
            player.sendMessage(MessageUtils.getMessage("tpa-already-pending"));
            return true;
        }

        tpaRequests.put(target.getUniqueId(), new TpaRequest(player.getUniqueId()));
        target.sendMessage(MessageUtils.formatMessage("tpa-received", "%player%", player.getName()));
        player.sendMessage(MessageUtils.formatMessage("tpa-sent", "%player%", target.getName()));

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("NexusPlugin"), () -> {
            TpaRequest request = tpaRequests.get(target.getUniqueId());
            if (request != null && request.sender.equals(player.getUniqueId())) {
                tpaRequests.remove(target.getUniqueId());
                
                Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
                Player senderPlayer = Bukkit.getPlayer(player.getUniqueId());
                
                if (targetPlayer != null) {
                    targetPlayer.sendMessage(MessageUtils.formatMessage("tpa-expired-target", "%player%", player.getName()));
                }
                if (senderPlayer != null) {
                    senderPlayer.sendMessage(MessageUtils.formatMessage("tpa-expired-sender", "%player%", target.getName()));
                }
            }
        }, 20L * 60);

        return true;
    }

    public static TpaRequest getRequest(UUID targetUUID) {
        return tpaRequests.get(targetUUID);
    }

    public static void removeRequest(UUID targetUUID) {
        tpaRequests.remove(targetUUID);
    }

    private boolean hasPendingRequest(UUID targetUUID) {
        TpaRequest request = tpaRequests.get(targetUUID);
        if (request == null) return false;
        
        if (System.currentTimeMillis() - request.timestamp > 60000) {
            tpaRequests.remove(targetUUID);
            return false;
        }
        return true;
    }

    public static class TpaRequest {
        private final UUID sender;
        private final long timestamp;

        public TpaRequest(UUID sender) {
            this.sender = sender;
            this.timestamp = System.currentTimeMillis();
        }

        public UUID getSender() {
            return sender;
        }
    }
} 