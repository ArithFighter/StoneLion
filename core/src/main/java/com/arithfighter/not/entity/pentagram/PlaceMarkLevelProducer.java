package com.arithfighter.not.entity.pentagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class PlaceMarkLevelProducer {
    private final int length;
    private int midLevelQuantity = 0;

    public PlaceMarkLevelProducer(int length) {
        this.length = length;
    }

    public void setMidLevelQuantity(int midLevelQuantity) {
        this.midLevelQuantity = midLevelQuantity;
    }

    public List<EnchantmentLevel> getLevelList() {
        EnchantmentLevel[] enchantmentLevels = getEnchantmentLevels();

        List<EnchantmentLevel> levelList = new ArrayList<>(Arrays.asList(enchantmentLevels));

        Collections.shuffle(levelList);

        return levelList;
    }

    private EnchantmentLevel[] getEnchantmentLevels() {
        EnchantmentLevel[] enchantmentLevels = new EnchantmentLevel[length];

        for (int i = 0; i < enchantmentLevels.length; i++) {
            if (i < midLevelQuantity+1)
                enchantmentLevels[i] = EnchantmentLevel.MID;
            else
                enchantmentLevels[i] = EnchantmentLevel.LOW;
        }
        return enchantmentLevels;
    }
}
