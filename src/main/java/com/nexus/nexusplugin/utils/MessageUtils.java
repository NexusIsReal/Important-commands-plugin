package com.nexus.nexusplugin.utils;

import com.nexus.nexusplugin.NexusPlugin;
import org.bukkit.ChatColor;

public class MessageUtils {
    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', 
            NexusPlugin.getInstance().getConfig().getString("messages." + path, "Message not found: " + path));
    }

    public static String formatMessage(String path, String... replacements) {
        String message = getMessage(path);
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }
        return message;
    }
} 