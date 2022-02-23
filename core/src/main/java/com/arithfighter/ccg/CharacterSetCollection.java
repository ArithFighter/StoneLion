package com.arithfighter.ccg;

import static com.arithfighter.ccg.CharacterList.*;

public class CharacterSetCollection {
    CharacterList[] characters = {
            KNIGHT,
            ROGUE,
            HUNTER,
            PALADIN,
            WARRIOR
    };
    int[][] numberSets = {
            KNIGHT.numberSet,
            ROGUE.numberSet,
            HUNTER.numberSet,
            PALADIN.numberSet,
            WARRIOR.numberSet
    };

    public int[] getCharacterSet(CharacterList character){
        int[] numberSet = new int[]{numberSets[0].length};

        for (int i = 0; i<characters.length;i++){
            if (character==characters[i]){
                numberSet = numberSets[i];
            }
        }
        return numberSet;
    }
}
