package com.nexus.nexusplugin.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtils {
    public static void notifyPlayers(CommandSender sender, Player target, String selfMessage, String otherMessage, String... replacements) {
        target.sendMessage(MessageUtils.formatMessage(selfMessage, replacements));
        if (sender != target) {
            sender.sendMessage(MessageUtils.formatMessage(otherMessage, replacements));
        }
    }
} 