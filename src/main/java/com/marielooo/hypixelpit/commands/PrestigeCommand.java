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
            sender.sendMessage(ChatColor.RED + "Only players may prestige.");
            return true;
        }

        Player player = (Player) sender;
        PlayerData data = PlayerData.get(player.getUniqueId());

        if (!data.canPrestige()) {
            player.sendMessage(ChatColor.RED + "You must reach level 120 before you can prestige.");
            return true;
        }

        data.prestige();
        player.sendMessage(ChatColor.GOLD + "Prestige successful! " + ChatColor.YELLOW + "You are now " + data.getPrestigeDisplay());
        player.sendMessage(ChatColor.GREEN + "You were awarded renown and a new perk slot.");
        return true;
    }
}
