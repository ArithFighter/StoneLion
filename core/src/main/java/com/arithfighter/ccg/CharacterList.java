package com.arithfighter.ccg;

import com.arithfighter.ccg.number.RandomNumProducer;

public enum CharacterList {
    KNIGHT(new int[]{2,3,9,0}),
    ROGUE(new int[] {-1,3,8,0}),
    HUNTER(new int[] {2,3,5,12}),
    PALADIN(new int[]{-1,2,7,15}),
    WARRIOR(new int[]{-7,-3,-1, new RandomNumProducer().getMax()});

    final int[] numberSet;

    CharacterList(int[] numberSet) {
        this.numberSet = numberSet;
    }
}
