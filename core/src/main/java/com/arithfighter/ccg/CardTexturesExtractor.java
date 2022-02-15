package com.arithfighter.ccg;

import com.badlogic.gdx.graphics.Texture;

public class CardTexturesExtractor {
    Texture[] cardTextures;

    public CardTexturesExtractor(Texture[] cardTextures){
        this.cardTextures = cardTextures;
    }
    
    public Texture[] getCardSet(CharacterList character){
        Texture[] cardSet = new Texture[4];
        
        switch (character){
            case KNIGHT:
                cardSet = getKnightCardSet();
                break;
            case ROGUE:
                cardSet = getRogueCardSet();
                break;
            case HUNTER:
                cardSet = getHunterCardSet();
                break;
            case PALADIN:
                cardSet = getPaladinCardSet();
                break;
            case WARRIOR:
                cardSet = getWarriorCardSet();
                break;
        }
        
        return cardSet;
    }

    private Texture[] getKnightCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[2];
        cardSet[1] = cardTextures[3];
        cardSet[2] = cardTextures[9];
        cardSet[3] = cardTextures[0];

        return cardSet;
    }

    private Texture[] getRogueCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[12];
        cardSet[1] = cardTextures[3];
        cardSet[2] = cardTextures[8];
        cardSet[3] = cardTextures[0];

        return cardSet;
    }

    private Texture[] getHunterCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[2];
        cardSet[1] = cardTextures[3];
        cardSet[2] = cardTextures[5];
        cardSet[3] = cardTextures[10];

        return cardSet;
    }

    private Texture[] getPaladinCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[12];
        cardSet[1] = cardTextures[2];
        cardSet[2] = cardTextures[7];
        cardSet[3] = cardTextures[11];

        return cardSet;
    }

    private Texture[] getWarriorCardSet(){
        Texture[] cardSet = new Texture[4];

        cardSet[0] = cardTextures[12];
        cardSet[1] = cardTextures[13];
        cardSet[2] = cardTextures[14];
        cardSet[3] = cardTextures[15];

        return cardSet;
    }

    public void dispose(){
        for (Texture texture:cardTextures)
            texture.dispose();
    }
}
