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

    public Texture[] getRogueCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[12];
        cardSet[1] = cardTextures[3];
        cardSet[2] = cardTextures[8];
        cardSet[3] = cardTextures[0];

        return cardSet;
    }

    public Texture[] getHunterCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[2];
        cardSet[1] = cardTextures[3];
        cardSet[2] = cardTextures[6];
        cardSet[3] = cardTextures[10];

        return cardSet;
    }

    public Texture[] getPaladinCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[12];
        cardSet[1] = cardTextures[2];
        cardSet[2] = cardTextures[7];
        cardSet[3] = cardTextures[11];

        return cardSet;
    }

    public Texture[] getWarriorCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[12];
        cardSet[1] = cardTextures[13];
        cardSet[2] = cardTextures[14];
        cardSet[3] = cardTextures[15];

        return cardSet;
    }
}
