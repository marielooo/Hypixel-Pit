package com.marielooo.hypixelpit.listeners;

import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerData.get(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerData pd = PlayerData.getIfLoaded(e.getPlayer().getUniqueId());
        if (pd != null) pd.save();
    }
}
