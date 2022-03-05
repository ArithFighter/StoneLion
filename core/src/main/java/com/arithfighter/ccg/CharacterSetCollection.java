package com.arithfighter.ccg;

public class CharacterSetCollection {
    int[][] numberSets = {
            CharacterList.KNIGHT.numberSet,
            CharacterList.ROGUE.numberSet,
            CharacterList.HUNTER.numberSet,
            CharacterList.PALADIN.numberSet,
            CharacterList.WARRIOR.numberSet
    };

    public int[] getCharacterSet(CharacterList character){
        int[] numberSet = new int[]{numberSets[0].length};

        for (int i = 0; i< CharacterList.values().length;i++){
            if (character==CharacterList.values()[i]){
                numberSet = numberSets[i];
            }
        }
        return numberSet;
    }
}
