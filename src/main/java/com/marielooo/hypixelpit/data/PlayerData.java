package com.marielooo.hypixelpit.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerData {

    private static final Map<UUID, PlayerData> CACHE = new ConcurrentHashMap<>();

    private final UUID uuid;
    private int level = 1;
    private long xp = 0;
    private long gold = 0;
    private int prestige = 0;
    private long renown = 0;

    private File file;
    private YamlConfiguration conf;

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

    private void load() {
        File dir = new File("plugins/HypixelPit/playerdata");
        if (!dir.exists()) dir.mkdirs();
        file = new File(dir, uuid.toString() + ".yml");
        if (!file.exists()) {
            conf = new YamlConfiguration();
            save();
            return;
        }
        conf = YamlConfiguration.loadConfiguration(file);
        level = conf.getInt("level", 1);
        xp = conf.getLong("xp", 0L);
        gold = conf.getLong("gold", 0L);
        prestige = conf.getInt("prestige", 0);
        renown = conf.getLong("renown", 0L);
    }

    public void save() {
        if (conf == null) conf = new YamlConfiguration();
        conf.set("level", level);
        conf.set("xp", xp);
        conf.set("gold", gold);
        conf.set("prestige", prestige);
        conf.set("renown", renown);
        try {
            conf.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save playerdata for " + uuid.toString());
            e.printStackTrace();
        }
    }

    // Basic getters/setters
    public UUID getUuid() { return uuid; }
    public int getLevel() { return level; }
    public long getXp() { return xp; }
    public long getGold() { return gold; }
    public int getPrestige() { return prestige; }
    public long getRenown() { return renown; }

    public void addXp(long amount) {
        if (amount <= 0) return;
        xp += amount;
        // Simple leveling: every 1000 xp = 1 level (placeholder, will be replaced with Pit formula)
        while (level < 120 && xp >= xpForNextLevel()) {
            xp -= xpForNextLevel();
            level++;
            // cap check
            if (level > 120) level = 120;
        }
    }

    private long xpForNextLevel() {
        // Placeholder formula: base 1000 scaled by prestige multiplier
        double prestigeMultiplier = 1.0 + (prestige * 0.1); // Prestige I = 1.1x
        return (long) Math.round(1000 * prestigeMultiplier);
    }

    public void addGold(long amount) { if (amount == 0) return; gold = Math.max(0, gold + amount); }
    public void setLevel(int lvl) { level = Math.max(1, Math.min(120, lvl)); }
    public void setXp(long xp) { this.xp = Math.max(0, xp); }
    public void setGold(long gold) { this.gold = Math.max(0, gold); }

    public boolean canPrestige() { return level >= 120 && prestige < 35; }

    public void doPrestige() {
        if (!canPrestige()) throw new IllegalStateException("Cannot prestige");
        prestige++;
        // award renown: simple formula for scaffold
        renown += 5 + prestige; // placeholder awards
        // reset gold, level, xp
        gold = 0;
        level = 1;
        xp = 0;
        // passives reset would be handled elsewhere
        save();
    }

    public String getLevelColored() {
        // Map level ranges to color codes according to spec
        int l = level;
        if (l >= 1 && l <= 9) return ChatColor.GRAY + String.valueOf(l);
        if (l >= 10 && l <= 19) return ChatColor.BLUE + String.valueOf(l);
        if (l >= 20 && l <= 29) return ChatColor.DARK_AQUA + String.valueOf(l);
        if (l >= 30 && l <= 39) return ChatColor.GREEN + String.valueOf(l);
        if (l >= 40 && l <= 49) return ChatColor.YELLOW + String.valueOf(l);
        if (l >= 50 && l <= 59) return ChatColor.GOLD + String.valueOf(l);
        if (l >= 60 && l <= 69) return ChatColor.RED + String.valueOf(l);
        if (l >= 70 && l <= 79) return ChatColor.DARK_RED + String.valueOf(l);
        if (l >= 80 && l <= 89) return ChatColor.DARK_PURPLE + String.valueOf(l);
        if (l >= 90 && l <= 99) return ChatColor.LIGHT_PURPLE + String.valueOf(l);
        if (l >= 100 && l <= 109) return ChatColor.WHITE + String.valueOf(l);
        if (l >= 110 && l <= 119) return ChatColor.DARK_BLUE + String.valueOf(l); // Bold Blue approximated
        if (l >= 120) return ChatColor.BOLD + "" + ChatColor.RED + String.valueOf(l); // Bold Red for 120
        return String.valueOf(l);
    }
}
