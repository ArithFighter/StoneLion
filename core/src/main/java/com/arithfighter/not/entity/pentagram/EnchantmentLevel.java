package com.arithfighter.not.entity.pentagram;

import com.badlogic.gdx.graphics.Color;

public enum EnchantmentLevel {
    NONE(Color.GRAY, 0, 0),
    LOW(Color.GREEN, 1, 3),
    MID(Color.BLUE, 4, 6),
    HIGH(Color.PURPLE, 7, 9);

    private final Color color;
    private final int minBell;
    private final int maxBell;

    EnchantmentLevel(Color color, int minBell, int maxBell) {
        this.color = color;
        this.minBell = minBell;
        this.maxBell = maxBell;
    }

    public Color getColor() {
        return color;
    }

    public int getMinBell() {
        return minBell;
    }

    public int getMaxBell() {
        return maxBell;
    }
}
