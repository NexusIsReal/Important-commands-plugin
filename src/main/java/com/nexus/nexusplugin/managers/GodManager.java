package com.nexus.nexusplugin.managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodManager {
    private final Set<UUID> godPlayers = new HashSet<>();
    private final Plugin plugin;

    public GodManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean toggleGodMode(Player player) {
        boolean godMode = !player.isInvulnerable();
        player.setInvulnerable(godMode);
        
        if (godMode) {
            godPlayers.add(player.getUniqueId());
            player.setFoodLevel(20);
            player.setSaturation(20f);
        } else {
            godPlayers.remove(player.getUniqueId());
        }
        
        return godMode;
    }

    public boolean isInGodMode(UUID playerUUID) {
        return godPlayers.contains(playerUUID);
    }

    public void removeGodMode(UUID playerUUID) {
        godPlayers.remove(playerUUID);
    }
} 