package com.marielooo.hypixelpit.manager;

import com.marielooo.hypixelpit.data.PlayerData;
import org.bukkit.entity.Player;

public final class PitEconomy {

    private PitEconomy() {
    }

    public static long calculateXpReward(PlayerData data) {
        double prestigeBonus = 1.0D + (data.getPrestige() * 0.12D);
        double streakBonus = 1.0D + (data.getStreak() * 0.025D);
        return Math.round(150D * prestigeBonus * streakBonus);
    }

    public static long calculateGoldReward(PlayerData data) {
        double prestigeBonus = 1.0D + (data.getPrestige() * 0.08D);
        double streakBonus = 1.0D + (data.getStreak() * 0.02D);
        return Math.round(35D * prestigeBonus * streakBonus);
    }

    public static void awardKill(Player killer, Player victim) {
        if (killer == null || victim == null || killer.equals(victim)) {
            return;
        }

        PlayerData killerData = PlayerData.get(killer.getUniqueId());
        PlayerData victimData = PlayerData.get(victim.getUniqueId());

        long xpReward = calculateXpReward(killerData);
        long goldReward = calculateGoldReward(killerData);

        killerData.addXp(xpReward);
        killerData.addGold(goldReward);
        killerData.incrementStreak();
        killerData.save();

        victimData.resetStreak();
        victimData.save();
    }
}
