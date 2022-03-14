package com.arithfighter.ccg.system;

public class CharacterSetService {
    public int[] getCharacterSet(CharacterList player) {
        CharacterList[] characters = CharacterList.values();

        int[] numberSet = new int[]{characters[0].numberSet.length};

        for (CharacterList character : characters) {
            if (player == character)
                numberSet = character.numberSet;
        }

        return numberSet;
    }
}
