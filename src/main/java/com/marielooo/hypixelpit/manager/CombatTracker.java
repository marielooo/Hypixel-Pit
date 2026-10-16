package com.marielooo.hypixelpit.manager;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks damage dealt between players to allow assist rewards.
 * Records cumulative damage and last-hit timestamps per victim -> attacker.
 */
public class CombatTracker {

    // victim -> (attacker -> cumulativeDamage)
    private static final Map<UUID, Map<UUID, Double>> DAMAGE = new ConcurrentHashMap<>();
    // victim -> (attacker -> lastHitMillis)
    private static final Map<UUID, Map<UUID, Long>> LAST_HIT = new ConcurrentHashMap<>();

    private CombatTracker() {}

    public static void recordDamage(UUID attacker, UUID victim, double amount) {
        if (attacker == null || victim == null) return;
        if (attacker.equals(victim)) return; // ignore self-damage

        DAMAGE.computeIfAbsent(victim, k -> new ConcurrentHashMap<UUID, Double>())
                .merge(attacker, amount, Double::sum);
        LAST_HIT.computeIfAbsent(victim, k -> new ConcurrentHashMap<UUID, Long>())
                .put(attacker, System.currentTimeMillis());
    }

    /**
     * Returns a map of attacker UUID -> damage amount for contributors within the time window (ms)
     */
    public static Map<UUID, Double> getContributorsWithin(UUID victim, long windowMs) {
        Map<UUID, Double> out = new HashMap<>();
        Map<UUID, Double> dmg = DAMAGE.get(victim);
        Map<UUID, Long> times = LAST_HIT.get(victim);
        if (dmg == null || times == null) return out;

        long now = System.currentTimeMillis();
        for (Map.Entry<UUID, Double> e : dmg.entrySet()) {
            UUID attacker = e.getKey();
            Double totalDamage = e.getValue();
            Long last = times.get(attacker);
            if (last == null) continue;
            if (now - last <= windowMs) {
                out.put(attacker, totalDamage);
            }
        }
        return out;
    }

    public static void clearVictim(UUID victim) {
        DAMAGE.remove(victim);
        LAST_HIT.remove(victim);
    }
}
