package com.marielooo.hypixelpit.manager;

import com.marielooo.hypixelpit.model.PitPerkType;
import java.util.HashMap;
import java.util.Map;

public class PitPerkManager {

    private final Map<PitPerkType, Integer> perkLevels = new HashMap<PitPerkType, Integer>();

    public PitPerkManager() {
        for (PitPerkType perkType : PitPerkType.values()) {
            perkLevels.put(perkType, 0);
        }
    }

    public int getPerkLevel(PitPerkType perkType) {
        return perkLevels.containsKey(perkType) ? perkLevels.get(perkType) : 0;
    }

    public void addPerkLevel(PitPerkType perkType, int amount) {
        if (amount <= 0 || !perkLevels.containsKey(perkType)) {
            return;
        }
        int current = perkLevels.get(perkType);
        perkLevels.put(perkType, current + amount);
    }

    public void setPerkLevel(PitPerkType perkType, int value) {
        if (!perkLevels.containsKey(perkType)) {
            return;
        }
        perkLevels.put(perkType, value < 0 ? 0 : value);
    }

    public int getTotalSlots() {
        return 4;
    }
}
