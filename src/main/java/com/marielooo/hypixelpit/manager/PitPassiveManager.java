package com.marielooo.hypixelpit.manager;

import com.marielooo.hypixelpit.model.PitPassiveType;
import com.marielooo.hypixelpit.model.PitPerkType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PitPassiveManager {

    private PitPassiveManager() {
    }

    public static int getMaxPassiveLevel() {
        return 5;
    }

    public static boolean canUpgrade(int currentLevel) {
        return currentLevel < getMaxPassiveLevel();
    }

    public static double getPassiveMultiplier(PitPassiveType type, int level) {
        if (level <= 0) {
            return 1.0D;
        }
        switch (type) {
            case XP_BOOST:
                return 1.0D + (0.10D * level);
            case GOLD_BOOST:
                return 1.0D + (0.10D * level);
            case MELEE_DAMAGE:
                return 1.0D + (0.01D * level);
            case BOW_DAMAGE:
                return 1.0D + (0.03D * level);
            case DAMAGE_REDUCTION:
                return 1.0D - (0.01D * level);
            case BUILD_BATTLER:
                return 1.0D + (0.60D * level);
            default:
                return 1.0D;
        }
    }

    public static List<PitPerkType> getDefaultPerkLoadout() {
        List<PitPerkType> perks = new ArrayList<PitPerkType>();
        Collections.addAll(perks,
                PitPerkType.GOLDEN_HEADS,
                PitPerkType.VAMPIRE,
                PitPerkType.STRENGTH_CHAINING,
                PitPerkType.GLADIATOR
        );
        return perks;
    }
}
