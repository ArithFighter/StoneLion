package com.arithfighter.not.entity.player;

import com.arithfighter.not.system.GameNumProducer;

public enum CharacterList {
    SNAKE(new int[]{2,3,9,0}, new int[]{2,3,9,0}),
    CRANE(new int[] {-1,3,8,0}, new int[]{12,3,8,0}),
    WOLF(new int[] {2,3,5,12}, new int[]{2,3,5,10}),
    CAT( new int[]{-1,2,7,15}, new int[]{12,2,7,11}),
    TURTLE(new int[]{-7,-3,-1, new GameNumProducer().getMaxNumber()}, new int[]{14,13,12,15});

    final int[] numberSet;
    final int[] textureMap;

    CharacterList(int[] numberSet, int[] textureMap) {
        this.numberSet = numberSet;
        this.textureMap = textureMap;
    }

    public int[] getNumberSet() {
        return numberSet;
    }

    public int[] getTextureMap() {
        return textureMap;
    }
}
