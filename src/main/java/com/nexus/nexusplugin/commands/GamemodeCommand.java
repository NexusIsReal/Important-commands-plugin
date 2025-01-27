package com.nexus.nexusplugin.commands;

import com.nexus.nexusplugin.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GamemodeCommand implements CommandExecutor, TabCompleter {
    private final List<String> gameModes = Arrays.stream(GameMode.values())
            .map(gameMode -> gameMode.name().toLowerCase())
            .collect(Collectors.toList());

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nexusplugin.gamemode")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }

        if (args.length < 1) return false;

        GameMode gameMode;
        Player target;
        int gameModeArgIndex = 0;
        int playerArgIndex = 1;

        if (args.length > 1) {
            try {
                gameMode = parseGameMode(args[0]);
            } catch (IllegalArgumentException e) {
                try {
                    gameMode = parseGameMode(args[1]);
                    gameModeArgIndex = 1;
                    playerArgIndex = 0;
                } catch (IllegalArgumentException ex) {
                    return false;
                }
            }
        } else {
            try {
                gameMode = parseGameMode(args[0]);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        if (args.length > playerArgIndex) {
            target = Bukkit.getPlayer(args[playerArgIndex]);
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

        target.setGameMode(gameMode);
        target.sendMessage(MessageUtils.formatMessage("gamemode-changed", "%gamemode%", gameMode.toString().toLowerCase()));

        if (sender != target) {
            sender.sendMessage(MessageUtils.formatMessage("gamemode-changed-other", 
                "%player%", target.getName(),
                "%gamemode%", gameMode.toString().toLowerCase()));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            for (String gameMode : gameModes) {
                if (gameMode.startsWith(input)) {
                    completions.add(gameMode);
                }
            }
        } else if (args.length == 2) {
            String input = args[1].toLowerCase();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(input)) {
                    completions.add(player.getName());
                }
            }
        }
        
        return completions;
    }

    private GameMode parseGameMode(String input) {
        try {
            return GameMode.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            try {
                int gameModeId = Integer.parseInt(input);
                return switch (gameModeId) {
                    case 0 -> GameMode.SURVIVAL;
                    case 1 -> GameMode.CREATIVE;
                    case 2 -> GameMode.ADVENTURE;
                    case 3 -> GameMode.SPECTATOR;
                    default -> throw new IllegalArgumentException();
                };
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException();
            }
        }
    }
} 