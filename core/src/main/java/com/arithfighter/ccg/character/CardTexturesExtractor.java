package com.arithfighter.ccg.character;

import com.badlogic.gdx.graphics.Texture;

public class CardTexturesExtractor {
    private final Texture[] cardTextures;
    private final static int quantity = CharacterList.KNIGHT.numberSet.length;

    public CardTexturesExtractor(Texture[] cardTextures) {
        this.cardTextures = cardTextures;
    }

    public Texture[] getCardSet(CharacterList player) {
        Texture[] cardSet = new Texture[quantity];

        CharacterList[] characterList = CharacterList.values();

        for (CharacterList character : characterList)
            if (player == character) {
                for (int j = 0; j < quantity; j++)
                    cardSet[j] = cardTextures[character.textureMap[j]];
            }

        return cardSet;
    }

    public void dispose() {
        for (Texture texture : cardTextures)
            texture.dispose();
    }
}
