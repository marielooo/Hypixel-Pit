package com.marielooo.hypixelpit.commands;

import com.marielooo.hypixelpit.manager.RenownShop;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RenownShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RenownShop shop = new RenownShop();
        sender.sendMessage(ChatColor.GOLD + "=== Renown Shop ===");
        sender.sendMessage(ChatColor.YELLOW + "tenacity" + ChatColor.GRAY + " - " + shop.getPrice("tenacity") + " Renown");
        sender.sendMessage(ChatColor.YELLOW + "scam_artist" + ChatColor.GRAY + " - " + shop.getPrice("scam_artist") + " Renown");
        sender.sendMessage(ChatColor.YELLOW + "extra_perk_slot" + ChatColor.GRAY + " - " + shop.getPrice("extra_perk_slot") + " Renown");
        sender.sendMessage(ChatColor.YELLOW + "divine_intervention" + ChatColor.GRAY + " - " + shop.getPrice("divine_intervention") + " Renown");
        sender.sendMessage(ChatColor.YELLOW + "apollo" + ChatColor.GRAY + " - " + shop.getPrice("apollo") + " Renown");
        return true;
    }
}
