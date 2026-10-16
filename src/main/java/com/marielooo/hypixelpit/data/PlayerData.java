package com.marielooo.hypixelpit.data;

import com.marielooo.hypixelpit.HypixelPit;
import com.marielooo.hypixelpit.manager.PitLeveling;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerData {

    private static final Map<UUID, PlayerData> CACHE = new ConcurrentHashMap<>();

    private final UUID uuid;
    private int level = 1;
    private int prestige = 0;
    private long xp = 0L;
    private long gold = 0L;
    private long renown = 0L;
    private int perkSlots = 4;
    private int streak = 0;

    private File file;
    private YamlConfiguration config;

    private PlayerData(UUID uuid) {
        this.uuid = uuid;
        load();
    }

    public static PlayerData get(UUID uuid) {
        return CACHE.computeIfAbsent(uuid, PlayerData::new);
    }

    public static PlayerData getIfLoaded(UUID uuid) {
        return CACHE.get(uuid);
    }

    public void load() {
        File dir = new File(HypixelPit.getInstance().getDataFolder(), "playerdata");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir, uuid.toString() + ".yml");
        if (!file.exists()) {
            config = new YamlConfiguration();
            save();
            return;
        }

        config = YamlConfiguration.loadConfiguration(file);
        level = config.getInt("level", 1);
        prestige = config.getInt("prestige", 0);
        xp = config.getLong("xp", 0L);
        gold = config.getLong("gold", 0L);
        renown = config.getLong("renown", 0L);
        perkSlots = config.getInt("perk-slots", 4);
        streak = config.getInt("streak", 0);
    }

    public void save() {
        if (config == null) {
            config = new YamlConfiguration();
        }
        config.set("level", level);
        config.set("prestige", prestige);
        config.set("xp", xp);
        config.set("gold", gold);
        config.set("renown", renown);
        config.set("perk-slots", perkSlots);
        config.set("streak", streak);

        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Unable to save player data for " + uuid.toString());
            e.printStackTrace();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLevel() {
        return level;
    }

    public int getPrestige() {
        return prestige;
    }

    public long getXp() {
        return xp;
    }

    public long getGold() {
        return gold;
    }

    public long getRenown() {
        return renown;
    }

    public int getPerkSlots() {
        return perkSlots;
    }

    public int getStreak() {
        return streak;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(level, PitLeveling.MAX_LEVEL));
    }

    public void setPrestige(int prestige) {
        this.prestige = Math.max(0, Math.min(prestige, PitLeveling.MAX_PRESTIGE));
    }

    public void addXp(long amount) {
        if (amount <= 0) {
            return;
        }
        xp += amount;
        while (level < PitLeveling.MAX_LEVEL && xp >= getXpForNextLevel()) {
            xp -= getXpForNextLevel();
            level++;
        }
        if (level >= PitLeveling.MAX_LEVEL) {
            level = PitLeveling.MAX_LEVEL;
            xp = 0L;
        }
    }

    public void addGold(long amount) {
        if (amount <= 0) {
            return;
        }
        gold += amount;
    }

    public void addRenown(long amount) {
        if (amount <= 0) {
            return;
        }
        renown += amount;
    }

    public void incrementStreak() {
        streak++;
    }

    public void resetStreak() {
        streak = 0;
    }

    public boolean canPrestige() {
        return level >= PitLeveling.MAX_LEVEL && prestige < PitLeveling.MAX_PRESTIGE;
    }

    public double getXpForNextLevel() {
        return PitLeveling.getXpForNextLevel(level, prestige);
    }

    public void prestige() {
        if (!canPrestige()) {
            throw new IllegalStateException("Player is not eligible to prestige.");
        }

        prestige++;
        level = 1;
        xp = 0L;
        gold = 0L;
        streak = 0;

        renown += 25L + prestige;
        perkSlots = Math.min(perkSlots + 1, 8);
        save();
    }

    public String getLevelDisplay() {
        return PitLeveling.getLevelColor(level);
    }

    public String getPrestigeDisplay() {
        return PitLeveling.getPrestigeTitle(prestige);
    }
}
