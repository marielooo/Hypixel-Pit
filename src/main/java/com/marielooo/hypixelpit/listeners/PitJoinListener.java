package com.marielooo.hypixelpit.listeners;

import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PitJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerData.get(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData playerData = PlayerData.getIfLoaded(event.getPlayer().getUniqueId());
        if (playerData != null) {
            playerData.save();
        }
    }
}
