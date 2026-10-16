package com.marielooo.hypixelpit.listeners;

import com.marielooo.hypixelpit.manager.CombatTracker;
import com.marielooo.hypixelpit.manager.PitEconomy;
import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Arrow;

import java.util.Map;
import java.util.UUID;

public class PitCombatListener implements Listener {

    private static final long ASSIST_WINDOW_MS = 10_000L; // 10 seconds

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player victim = (Player) event.getEntity();

        Player attacker = null;

        if (event.getDamager() instanceof Player) {
            attacker = (Player) event.getDamager();
        } else if (event.getDamager() instanceof Projectile) {
            Projectile proj = (Projectile) event.getDamager();
            ProjectileSource src = proj.getShooter();
            if (src instanceof Player) {
                attacker = (Player) src;
            }
        }

        if (attacker == null) return;

        double dmg = event.getDamage();
        CombatTracker.recordDamage(attacker.getUniqueId(), victim.getUniqueId(), dmg);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        if (victim == null) return;

        Player killer = victim.getKiller();

        if (killer == null) {
            PlayerData victimData = PlayerData.get(victim.getUniqueId());
            victimData.resetStreak();
            victimData.save();
            CombatTracker.clearVictim(victim.getUniqueId());
            return;
        }

        // Award killer as before
        PitEconomy.awardKill(killer, victim);

        // Compute assists for other contributors within window
        Map<UUID, Double> contributors = CombatTracker.getContributorsWithin(victim.getUniqueId(), ASSIST_WINDOW_MS);
        // Remove killer itself
        contributors.remove(killer.getUniqueId());

        double totalDamage = 0.0;
        for (double v : contributors.values()) totalDamage += v;

        if (totalDamage > 0.0) {
            for (Map.Entry<UUID, Double> e : contributors.entrySet()) {
                UUID assisterUuid = e.getKey();
                double dmg = e.getValue();
                Player assister = org.bukkit.Bukkit.getPlayer(assisterUuid);
                if (assister == null) continue; // offline

                // Assist reward scales with damage share; base is half of normal kill reward
                PlayerData assisterData = PlayerData.get(assisterUuid);
                long assistXp = Math.round(PitEconomy.calculateXpReward(assisterData) * 0.5 * (dmg / totalDamage));
                long assistGold = Math.round(PitEconomy.calculateGoldReward(assisterData) * 0.5 * (dmg / totalDamage));

                assisterData.addXp(assistXp);
                assisterData.addGold(assistGold);
                assisterData.save();

                if (assister.isOnline()) {
                    assister.sendMessage(org.bukkit.ChatColor.GREEN + "You received " + assistXp + " XP and " + assistGold + "g for assisting in a kill.");
                }
            }
        }

        // Clear trackers for this victim
        CombatTracker.clearVictim(victim.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // Clean up to avoid memory leak
        CombatTracker.clearVictim(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // No-op; ensure data loaded elsewhere
    }
}
