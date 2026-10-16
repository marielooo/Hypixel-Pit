package com.marielooo.hypixelpit.listeners;

import com.marielooo.hypixelpit.manager.PitEconomy;
import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PitCombatListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        if (victim == null) {
            return;
        }

        Player killer = victim.getKiller();
        if (killer == null) {
            PlayerData victimData = PlayerData.get(victim.getUniqueId());
            victimData.resetStreak();
            victimData.save();
            return;
        }

        PitEconomy.awardKill(killer, victim);
    }
}
