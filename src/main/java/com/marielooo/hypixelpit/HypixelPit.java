package com.marielooo.hypixelpit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.marielooo.hypixelpit.data.PlayerData;
import com.marielooo.hypixelpit.listeners.JoinListener;
import com.marielooo.hypixelpit.commands.ViewCommand;
import com.marielooo.hypixelpit.commands.PrestigeCommand;

import java.util.UUID;
import java.util.logging.Logger;

public class HypixelPit extends JavaPlugin {

    private static HypixelPit instance;
    private Logger log;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        // Create data folders
        saveDefaultConfig();
        getDataFolder().mkdirs();

        // Register listeners and commands
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("view").setExecutor(new ViewCommand());
        getCommand("prestige").setExecutor(new PrestigeCommand());

        log.info("HypixelPit enabled.");
    }

    @Override
    public void onDisable() {
        // Save all loaded player data
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData pd = PlayerData.get(p.getUniqueId());
            if (pd != null) pd.save();
        }
        log.info("HypixelPit disabled.");
    }

    public static HypixelPit getInstance() {
        return instance;
    }
}
