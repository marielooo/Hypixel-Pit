package com.marielooo.hypixelpit.manager;

import com.marielooo.hypixelpit.model.MysticItemType;
import java.util.EnumMap;
import java.util.Map;

public class MysticItemManager {

    private final Map<MysticItemType, Integer> itemLives = new EnumMap<MysticItemType, Integer>(MysticItemType.class);
    private final Map<MysticItemType, Integer> enchantedTier = new EnumMap<MysticItemType, Integer>(MysticItemType.class);

    public MysticItemManager() {
        for (MysticItemType type : MysticItemType.values()) {
            itemLives.put(type, 20);
            enchantedTier.put(type, 0);
        }
    }

    public int getLives(MysticItemType type) {
        return itemLives.containsKey(type) ? itemLives.get(type) : 0;
    }

    public void damageItem(MysticItemType type, int amount) {
        if (!itemLives.containsKey(type)) {
            return;
        }
        int current = itemLives.get(type);
        int next = Math.max(0, current - amount);
        itemLives.put(type, next);
    }

    public boolean isDestroyed(MysticItemType type) {
        return getLives(type) <= 0;
    }

    public void protectWithFeather(MysticItemType type) {
        if (!itemLives.containsKey(type)) {
            return;
        }
        itemLives.put(type, Math.max(1, itemLives.get(type)));
    }

    public void addEnchantTier(MysticItemType type, int amount) {
        if (!enchantedTier.containsKey(type)) {
            return;
        }
        int current = enchantedTier.get(type);
        enchantedTier.put(type, Math.max(0, Math.min(3, current + amount)));
    }

    public int getEnchantTier(MysticItemType type) {
        return enchantedTier.containsKey(type) ? enchantedTier.get(type) : 0;
    }
}
