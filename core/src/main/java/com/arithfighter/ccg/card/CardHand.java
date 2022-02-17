package com.arithfighter.ccg.card;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Texture;

public class CardHand {
    private final NumberCard[] cards;

    public CardHand(int[] numberSet, Texture[] textures) {
        float initX = WindowSetting.CENTER_X + WindowSetting.GRID_X * 1.2f;
        float initY = -WindowSetting.GRID_Y;
        int cardQuantity = numberSet.length;

        cards = new NumberCard[cardQuantity];

        NumberCard sample = new NumberCard(initX, initY, textures[0], numberSet[0]);

        float padding = sample.getWidth() + WindowSetting.GRID_X*0.8f;

        for(int i = 0; i< cardQuantity;i++)
            cards[i] = new NumberCard(initX+i*padding, initY, textures[i], numberSet[i]);
    }

    public NumberCard[] getCards() {
        return cards;
    }
}
