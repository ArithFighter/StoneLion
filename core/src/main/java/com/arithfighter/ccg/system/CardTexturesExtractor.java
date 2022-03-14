package com.arithfighter.ccg.system;

import com.badlogic.gdx.graphics.Texture;

public class CardTexturesExtractor {
    private final Texture[] cardTextures;
    private final CharacterList[] characters = CharacterList.values();
    private final int quantity = characters[0].numberSet.length;

    public CardTexturesExtractor(Texture[] cardTextures) {
        this.cardTextures = cardTextures;
    }

    public Texture[] getCardSet(CharacterList player) {
        Texture[] cardSet = new Texture[quantity];

        for (CharacterList character : characters)
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
