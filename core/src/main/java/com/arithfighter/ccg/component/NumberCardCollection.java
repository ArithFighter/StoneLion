package com.arithfighter.ccg.component;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Texture;

public class NumberCardCollection {
    private final NumberCard[] cards;

    public NumberCardCollection(int min, int mid, int max, int reset, Texture[] textures) {
        float initX = WindowSetting.CENTER_X + WindowSetting.GRID_X * 1.2f;
        float initY = 0;

        NumberCard minCard, midCard, maxCard, resetCard;

        minCard = new NumberCard(initX, initY, textures[0], min);

        float padding = minCard.getWidth() + WindowSetting.GRID_X;

        midCard = new NumberCard(initX + padding, initY, textures[1], mid);

        maxCard = new NumberCard(initX + padding * 2, initY, textures[2], max);

        resetCard = new NumberCard(initX + padding * 3, initY, textures[3], reset);

        cards = new NumberCard[]{minCard, midCard, maxCard, resetCard};
    }

    public NumberCard[] getCards() {
        return cards;
    }
}
