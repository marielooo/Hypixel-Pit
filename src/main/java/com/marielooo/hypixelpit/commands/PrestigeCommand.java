package com.marielooo.hypixelpit.commands;

import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrestigeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player p = (Player) sender;
        PlayerData pd = PlayerData.get(p.getUniqueId());
        if (!pd.canPrestige()) {
            p.sendMessage(ChatColor.RED + "You must be level 120 and below Prestige XXXV to prestige.");
            return true;
        }
        pd.doPrestige();
        p.sendMessage(ChatColor.GREEN + "You have prestiged! New prestige: " + ChatColor.YELLOW + pd.getPrestige());
        p.sendMessage(ChatColor.GREEN + "You were awarded " + ChatColor.YELLOW + pd.getRenown() + ChatColor.GREEN + " renown total.");
        return true;
    }
}
