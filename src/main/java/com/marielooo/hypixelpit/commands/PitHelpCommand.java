package com.marielooo.hypixelpit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PitHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "=== Hypixel Pit Commands ===");
        sender.sendMessage(ChatColor.YELLOW + "/pitstats [player]" + ChatColor.GRAY + " - show a Pit profile");
        sender.sendMessage(ChatColor.YELLOW + "/prestige" + ChatColor.GRAY + " - prestige at level 120");
        sender.sendMessage(ChatColor.YELLOW + "/pithelp" + ChatColor.GRAY + " - show this help menu");
        return true;
    }
}
