package com.marielooo.hypixelpit.commands;

import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PitStatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = null;

        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
        } else if (sender instanceof Player) {
            target = (Player) sender;
        }

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Usage: /pitstats [player]");
            return true;
        }

        PlayerData data = PlayerData.get(target.getUniqueId());
        sender.sendMessage(ChatColor.GOLD + "=== Pit Profile: " + target.getName() + " ===");
        sender.sendMessage(ChatColor.YELLOW + "Level: " + ChatColor.RESET + data.getLevelDisplay() + " " + ChatColor.GRAY + "(" + data.getLevel() + ")");
        sender.sendMessage(ChatColor.YELLOW + "Prestige: " + ChatColor.RESET + data.getPrestigeDisplay());
        sender.sendMessage(ChatColor.YELLOW + "XP: " + ChatColor.RESET + data.getXp());
        sender.sendMessage(ChatColor.YELLOW + "Gold: " + ChatColor.RESET + data.getGold() + "g");
        sender.sendMessage(ChatColor.YELLOW + "Renown: " + ChatColor.RESET + data.getRenown());
        sender.sendMessage(ChatColor.YELLOW + "Perk Slots: " + ChatColor.RESET + data.getPerkSlots());
        sender.sendMessage(ChatColor.YELLOW + "Kill Streak: " + ChatColor.RESET + data.getStreak());
        return true;
    }
}
