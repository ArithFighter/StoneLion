package com.arithfighter.ccg;

import static com.arithfighter.ccg.CharacterList.*;

public class CharacterSetCollection {
    RandomNumProducer rnp = new RandomNumProducer();
    int[] knightSet = {2,3,9,0};
    int[] rogueSet = {-1,3,8,0};
    int[] hunterSet = {2,3,6,12};
    int[] paladinSet = {-1,2,7,15};
    int[] warriorSet = {-1,-3,-7, rnp.getMax()};
    CharacterList[] characters = {KNIGHT, ROGUE, HUNTER, PALADIN, WARRIOR};
    int[][] numberSets = {knightSet, rogueSet, hunterSet, paladinSet, warriorSet};

    public int[] getCharacterSet(CharacterList character){
        int[] numberSet = new int[]{knightSet.length};

        for (int i = 0; i<characters.length;i++){
            if (character==characters[i]){
                numberSet = numberSets[i];
            }
        }
        return numberSet;
    }
}
