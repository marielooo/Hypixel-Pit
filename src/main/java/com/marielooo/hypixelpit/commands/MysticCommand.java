package com.marielooo.hypixelpit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MysticCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "=== Mystic Well ===");
        sender.sendMessage(ChatColor.YELLOW + "Fresh Mystic Sword" + ChatColor.GRAY + " - 20 lives, tier 0" );
        sender.sendMessage(ChatColor.YELLOW + "Fresh Mystic Bow" + ChatColor.GRAY + " - 20 lives, tier 0");
        sender.sendMessage(ChatColor.YELLOW + "Fresh Pants" + ChatColor.GRAY + " - 20 lives, tier 0");
        sender.sendMessage(ChatColor.YELLOW + "Sewer Aqua Pants" + ChatColor.GRAY + " - 20 lives, tier 0");
        sender.sendMessage(ChatColor.YELLOW + "Dark Pants" + ChatColor.GRAY + " - 20 lives, tier 0");
        sender.sendMessage(ChatColor.GREEN + "Funky Feathers preserve item life on death when equipped.");
        return true;
    }
}
