package com.marielooo.hypixelpit.manager;

public final class PitLeveling {

    public static final int MAX_LEVEL = 120;
    public static final int MAX_PRESTIGE = 35;

    private PitLeveling() {
    }

    public static double getPrestigeMultiplier(int prestige) {
        if (prestige <= 0) {
            return 1.0D;
        }
        return Math.pow(1.1D, prestige);
    }

    public static double getXpForNextLevel(int level, int prestige) {
        double multiplier = getPrestigeMultiplier(prestige);
        return 1000.0D * multiplier * (1.0D + (level / 120.0D) * 0.25D);
    }

    public static String getLevelColor(int level) {
        if (level <= 9) return "§7" + level;
        if (level <= 19) return "§9" + level;
        if (level <= 29) return "§3" + level;
        if (level <= 39) return "§a" + level;
        if (level <= 49) return "§e" + level;
        if (level <= 59) return "§6" + level;
        if (level <= 69) return "§c" + level;
        if (level <= 79) return "§4" + level;
        if (level <= 89) return "§5" + level;
        if (level <= 99) return "§d" + level;
        if (level <= 109) return "§f" + level;
        if (level <= 119) return "§9§l" + level;
        return "§c§l" + level;
    }

    public static String getPrestigeTitle(int prestige) {
        if (prestige <= 0) {
            return "No Prestige";
        }
        return "Prestige " + prestige;
    }
}
