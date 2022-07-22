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
        List<EnchantmentLevel> levelList = new ArrayList<>(Arrays.asList(getEnchantmentLevels()));

        Collections.shuffle(levelList);

        return levelList;
    }

    private EnchantmentLevel[] getEnchantmentLevels() {
        EnchantmentLevel[] enchantmentLevels = new EnchantmentLevel[length];

        for (int i = 0; i < enchantmentLevels.length; i++)
            enchantmentLevels[i] = getEnchantmentLevel(i);

        return enchantmentLevels;
    }

    private EnchantmentLevel getEnchantmentLevel(int i){
        EnchantmentLevel enchantmentLevel;

        if (i < midLevelQuantity-1)
            enchantmentLevel = EnchantmentLevel.MID;
        else
            enchantmentLevel = EnchantmentLevel.LOW;

        return enchantmentLevel;
    }
}
