package com.marielooo.hypixelpit;

import com.marielooo.hypixelpit.commands.PitHelpCommand;
import com.marielooo.hypixelpit.commands.PitStatsCommand;
import com.marielooo.hypixelpit.commands.PrestigeCommand;
import com.marielooo.hypixelpit.commands.RenownShopCommand;
import com.marielooo.hypixelpit.commands.PassiveCommand;
import com.marielooo.hypixelpit.commands.MysticCommand;
import com.marielooo.hypixelpit.data.PlayerData;
import com.marielooo.hypixelpit.listeners.PitCombatListener;
import com.marielooo.hypixelpit.listeners.PitJoinListener;
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
        getServer().getPluginManager().registerEvents(new PitCombatListener(), this);

        if (getCommand("pitstats") != null) {
            getCommand("pitstats").setExecutor(new PitStatsCommand());
        }
        if (getCommand("prestige") != null) {
            getCommand("prestige").setExecutor(new PrestigeCommand());
        }
        if (getCommand("pithelp") != null) {
            getCommand("pithelp").setExecutor(new PitHelpCommand());
        }
        if (getCommand("renownshop") != null) {
            getCommand("renownshop").setExecutor(new RenownShopCommand());
        }
        if (getCommand("passive") != null) {
            getCommand("passive").setExecutor(new PassiveCommand());
        }
        if (getCommand("mystic") != null) {
            getCommand("mystic").setExecutor(new MysticCommand());
        }

        getLogger().info("HypixelPit v0.3.0 enabled.");
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData data = PlayerData.get(player.getUniqueId());
            if (data != null) {
                data.save();
            }
        }
        getLogger().info("HypixelPit v0.3.0 disabled.");
    }

    public static HypixelPit getInstance() {
        return instance;
    }
}
