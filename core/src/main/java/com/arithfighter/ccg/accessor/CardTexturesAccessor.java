package com.arithfighter.ccg.accessor;

import com.badlogic.gdx.graphics.Texture;

public class CardTexturesAccessor {
    Texture[] cardTextures;

    public CardTexturesAccessor(Texture[] cardTextures){
        this.cardTextures = cardTextures;
    }

    public Texture[] getKnightCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[2];
        cardSet[1] = cardTextures[3];
        cardSet[2] = cardTextures[9];
        cardSet[3] = cardTextures[0];

        return cardSet;
    }
}
