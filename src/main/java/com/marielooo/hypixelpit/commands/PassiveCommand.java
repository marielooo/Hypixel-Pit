package com.marielooo.hypixelpit.commands;

import com.marielooo.hypixelpit.data.PlayerData;
import com.marielooo.hypixelpit.model.PitPassiveType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PassiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can view passives.");
            return true;
        }

        Player player = (Player) sender;
        PlayerData data = PlayerData.get(player.getUniqueId());
        sender.sendMessage(ChatColor.GOLD + "=== Passive Upgrades ===");
        for (PitPassiveType type : PitPassiveType.values()) {
            sender.sendMessage(ChatColor.YELLOW + type.name() + ChatColor.GRAY + " - Lv." + data.getPassiveLevel(type));
        }
        return true;
    }
}
