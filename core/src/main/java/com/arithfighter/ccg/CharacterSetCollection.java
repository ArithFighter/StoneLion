package com.arithfighter.ccg;

import static com.arithfighter.ccg.CharacterList.*;

public class CharacterSetCollection {
    int[] knightSet = {2,3,9};
    int[] rogueSet = {-1,3,8};
    int[] hunterSet = {1,2,7};
    CharacterList[] characters = {KNIGHT, ROGUE, HUNTER};
    int[][] numberSets = {knightSet, rogueSet, hunterSet};

    public int[] getCharacterSet(CharacterList character){
        int[] numberSet = new int[]{3};
        for (int i = 0; i<characters.length;i++){
            if (character==characters[i]){
                numberSet = numberSets[i];
            }
        }
        return numberSet;
    }
}
