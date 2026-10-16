package com.marielooo.hypixelpit;

import com.marielooo.hypixelpit.commands.PitHelpCommand;
import com.marielooo.hypixelpit.commands.PitStatsCommand;
import com.marielooo.hypixelpit.commands.PrestigeCommand;
import com.marielooo.hypixelpit.listeners.PitJoinListener;
import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HypixelPit extends JavaPlugin {

    private static HypixelPit instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getDataFolder().mkdirs();

        getServer().getPluginManager().registerEvents(new PitJoinListener(), this);

        if (getCommand("pitstats") != null) {
            getCommand("pitstats").setExecutor(new PitStatsCommand());
        }
        if (getCommand("prestige") != null) {
            getCommand("prestige").setExecutor(new PrestigeCommand());
        }
        if (getCommand("pithelp") != null) {
            getCommand("pithelp").setExecutor(new PitHelpCommand());
        }

        getLogger().info("HypixelPit v0.2.0 enabled.");
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = PlayerData.get(player.getUniqueId());
            if (playerData != null) {
                playerData.save();
            }
        }
        getLogger().info("HypixelPit disabled.");
    }

    public static HypixelPit getInstance() {
        return instance;
    }
}
