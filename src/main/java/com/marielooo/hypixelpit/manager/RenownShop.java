package com.marielooo.hypixelpit.manager;

import com.marielooo.hypixelpit.model.PitPassiveType;
import java.util.HashMap;
import java.util.Map;

public class RenownShop {

    private final Map<String, Long> prices = new HashMap<String, Long>();

    public RenownShop() {
        prices.put("tenacity", 15L);
        prices.put("scam_artist", 20L);
        prices.put("extra_perk_slot", 25L);
        prices.put("extra_killstreak_slot", 30L);
        prices.put("divine_intervention", 35L);
        prices.put("heresy", 45L);
        prices.put("the_way", 50L);
        prices.put("apollo", 60L);
        prices.put("fancy_hat", 75L);
    }

    public long getPrice(String key) {
        if (key == null) {
            return 0L;
        }
        Long price = prices.get(key.toLowerCase());
        return price == null ? 0L : price.longValue();
    }

    public boolean canAfford(long renown, String key) {
        return renown >= getPrice(key);
    }

    public boolean purchase(long renown, String key) {
        return canAfford(renown, key);
    }

    public double getPassiveMultiplier(PitPassiveType type, int passiveLevel) {
        return PitPassiveManager.getPassiveMultiplier(type, passiveLevel);
    }
}
