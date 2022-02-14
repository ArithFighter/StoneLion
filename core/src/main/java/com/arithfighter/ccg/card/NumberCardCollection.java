package com.arithfighter.ccg.card;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Texture;

public class NumberCardCollection {
    private final NumberCard[] cards;

    public NumberCardCollection(int min, int mid, int max, int reset, Texture[] textures) {
        float initX = WindowSetting.CENTER_X + WindowSetting.GRID_X * 1.2f;
        float initY = -WindowSetting.GRID_Y;
        int [] numbers = {min, mid, max, reset};
        int cardQuantity = numbers.length;
        cards = new NumberCard[cardQuantity];

        NumberCard sample = new NumberCard(initX, initY, textures[0], min);

        float padding = sample.getWidth() + WindowSetting.GRID_X*0.8f;

        for(int i = 0; i< cardQuantity;i++)
            cards[i] = new NumberCard(initX+i*padding, initY, textures[i], numbers[i]);
    }

    public NumberCard[] getCards() {
        return cards;
    }
}
