package com.arithfighter.ccg;

public class CharacterSetService {
    public int[] getCharacterSet(CharacterList player) {
        int length = CharacterList.KNIGHT.numberSet.length;

        CharacterList[] characters = CharacterList.values();

        int[] numberSet = new int[]{length};

        for (CharacterList character : characters) {
            if (player == character)
                numberSet = character.numberSet;
        }

        return numberSet;
    }
}
