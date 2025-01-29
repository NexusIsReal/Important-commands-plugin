package com.nexus.nexusplugin.managers;

import com.nexus.nexusplugin.models.TpaRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaManager {
    private final Map<UUID, TpaRequest> tpaRequests = new HashMap<>();
    private final Plugin plugin;

    public TpaManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean hasPendingRequest(UUID targetUUID) {
        TpaRequest request = tpaRequests.get(targetUUID);
        if (request == null) return false;
        
        if (request.isExpired()) {
            tpaRequests.remove(targetUUID);
            return false;
        }
        return true;
    }

    public void createRequest(Player sender, Player target) {
        tpaRequests.put(target.getUniqueId(), new TpaRequest(sender.getUniqueId()));
        scheduleExpiration(sender, target);
    }

    public TpaRequest getRequest(UUID targetUUID) {
        TpaRequest request = tpaRequests.get(targetUUID);
        if (request != null && request.isExpired()) {
            tpaRequests.remove(targetUUID);
            return null;
        }
        return request;
    }

    public void removeRequest(UUID targetUUID) {
        tpaRequests.remove(targetUUID);
    }

    private void scheduleExpiration(Player sender, Player target) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TpaRequest request = tpaRequests.get(target.getUniqueId());
            if (request != null && request.getSender().equals(sender.getUniqueId())) {
                tpaRequests.remove(target.getUniqueId());
                
                Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
                Player senderPlayer = Bukkit.getPlayer(sender.getUniqueId());
                
                if (targetPlayer != null && targetPlayer.isOnline()) {
                    targetPlayer.sendMessage(com.nexus.nexusplugin.utils.MessageUtils.formatMessage(
                        "tpa-expired-target", "%player%", sender.getName()));
                }
                if (senderPlayer != null && senderPlayer.isOnline()) {
                    senderPlayer.sendMessage(com.nexus.nexusplugin.utils.MessageUtils.formatMessage(
                        "tpa-expired-sender", "%player%", target.getName()));
                }
            }
        }, 20L * 60);
    }
} 