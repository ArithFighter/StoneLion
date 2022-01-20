package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    TemplateCard whiteCard, greenCard, yellowCard, purpleCard;
    TemplateCard[] cards;
    float initX = WindowSetting.CENTER_X;
    float initY = 0;
    float padding;
    int[] numberSet = {2, 3, 9, 0};

    public Hand(Texture texture) {
        whiteCard = new TemplateCard(initX, initY, Color.WHITE, texture, String.valueOf(numberSet[0]));
        padding = whiteCard.getWidth() + WindowSetting.GRID_X;

        greenCard = new TemplateCard(initX + padding, initY, Color.GREEN, texture, String.valueOf(numberSet[1]));
        yellowCard = new TemplateCard(initX + padding * 2, initY, Color.YELLOW, texture, String.valueOf(numberSet[2]));
        purpleCard = new TemplateCard(initX + padding * 3, initY, Color.PURPLE, texture, String.valueOf(numberSet[3]));

        cards = new TemplateCard[]{whiteCard, greenCard, yellowCard, purpleCard};
    }

    public void draw(SpriteBatch batch) {
        for (TemplateCard card : cards)
            card.draw(batch);
    }

    public void checkTouchingCard(float x, float y) {
        for (TemplateCard card : cards)
            card.checkTouchingCard(x, y);
    }

    public void checkActive(float x, float y) {
        for (TemplateCard card : cards)
            card.checkActive(x, y);
    }

    public void updateWhenDrag(float x, float y) {
        for (TemplateCard card : cards)
            card.updateWhenDrag(x, y);
    }

    public void resetHand() {
        for (TemplateCard card : cards)
            card.resetCard();
    }

    public boolean isCardActive() {
        int condition = 0;
        for (int i = 0; i < cards.length; i++)
            if (cards[i].isActive())
                condition = i;

        return cards[condition].isActive();
    }

    public void dispose() {
        for (TemplateCard card : cards)
            card.dispose();
    }
}
