package com.arithfighter.ccg.card;

import com.arithfighter.ccg.CharacterList;
import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class CardHand {
    private final ArrayList<NumberCard> cardList = new ArrayList<>();
    float initX = WindowSetting.CENTER_X + WindowSetting.GRID_X * 1.2f;
    float initY = -WindowSetting.GRID_Y;

    public CardHand(int[] numberSet, Texture[] textures, CharacterList character) {
        for(int i = 0; i< numberSet.length; i++)
            cardList.add(new NumberCard(initX +i*getPadding(textures), initY, textures[i], numberSet[i]));

        getSkillCard(textures, character);
    }

    private void getSkillCard(Texture[] textures, CharacterList character){
        if (character == CharacterList.KNIGHT){
            NumberCard skillCard = new NumberCard(initX, initY, textures[4], 2);
            cardList.add(skillCard);
        }
    }

    private float getPadding(Texture[] textures){
        NumberCard sample = new NumberCard(0, 0, textures[0], 0);
        return sample.getWidth() + WindowSetting.GRID_X*0.8f;
    }

    public NumberCard[] getCards() {
        NumberCard[] cards = new NumberCard[cardList.size()];
        for (int i = 0; i< cardList.size();i++)
            cards[i] = cardList.get(i);
        return cards;
    }
}
