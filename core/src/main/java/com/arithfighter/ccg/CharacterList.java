package com.arithfighter.ccg;

import com.arithfighter.ccg.randomnum.RandomNumProducer;

public enum CharacterList {
    KNIGHT("Knight", new int[]{2,3,9,0}),
    ROGUE("Rogue", new int[] {-1,3,8,0}),
    HUNTER("Hunter", new int[] {2,3,5,12}),
    PALADIN("Paladin", new int[]{-1,2,7,15}),
    WARRIOR("Warrior", new int[]{-7,-3,-1, new RandomNumProducer().getMax()});

    final String name;
    final int[] numberSet;

    CharacterList(String name, int[] numberSet) {
        this.name = name;
        this.numberSet = numberSet;
    }
}
