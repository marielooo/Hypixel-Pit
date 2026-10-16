package com.marielooo.hypixelpit.commands;

import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ViewCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PlayerData pd = null;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Usage: /view <player>");
                return true;
            }
            Player p = (Player) sender;
            pd = PlayerData.get(p.getUniqueId());
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found or offline.");
                return true;
            }
            pd = PlayerData.get(target.getUniqueId());
        }

        sender.sendMessage(ChatColor.GOLD + "=== Pit Stats ===");
        sender.sendMessage(ChatColor.YELLOW + "Level: " + ChatColor.RESET + pd.getLevelColored());
        sender.sendMessage(ChatColor.YELLOW + "XP: " + ChatColor.RESET + pd.getXp());
        sender.sendMessage(ChatColor.YELLOW + "Gold: " + ChatColor.RESET + pd.getGold() + "g");
        sender.sendMessage(ChatColor.YELLOW + "Prestige: " + ChatColor.RESET + pd.getPrestige());
        sender.sendMessage(ChatColor.YELLOW + "Renown: " + ChatColor.RESET + pd.getRenown());
        return true;
    }
}
