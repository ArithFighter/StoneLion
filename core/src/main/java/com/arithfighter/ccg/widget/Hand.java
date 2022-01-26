package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    NumberCard whiteCard, greenCard, goldenCard, purpleCard;
    NumberCard[] cards;
    float initX = WindowSetting.CENTER_X+WindowSetting.GRID_X*3;
    float initY = 0;
    float padding;
    int[] numberSet = {2, 3, 9, 0};

    public Hand(Texture texture) {
        whiteCard = new NumberCard(initX, initY, Color.WHITE, texture, numberSet[0]);
        padding = whiteCard.getWidth() + WindowSetting.GRID_X/1.2f;

        greenCard = new NumberCard(initX + padding, initY, Color.GREEN, texture, numberSet[1]);
        goldenCard = new NumberCard(initX + padding * 2, initY, Color.GOLD, texture, numberSet[2]);
        purpleCard = new NumberCard(initX + padding * 3, initY, Color.PURPLE, texture, numberSet[3]);

        cards = new NumberCard[]{whiteCard, greenCard, goldenCard, purpleCard};
    }

    public void draw(SpriteBatch batch) {
        for (NumberCard card : cards)
            card.draw(batch);
    }

    public int getCardNumber(){
        return numberSet[getActiveCardIndex()];
    }

    public boolean isResetCard(){
        return getActiveCardIndex() == 3;
    }

    public void checkTouchingCard(float x, float y) {
        for (NumberCard card : cards)
            card.checkTouchingCard(x, y);
    }

    public void checkActive(float x, float y) {
        for (NumberCard card : cards)
            card.checkActive(x, y);
    }

    public void updateWhenDrag(float x, float y) {
        for (NumberCard card : cards)
            card.updateWhenDrag(x, y);
    }

    public void resetHand() {
        for (NumberCard card : cards)
            card.resetCard();
    }

    public boolean isCardActive() {
        return cards[getActiveCardIndex()].isActive();
    }

    private int getActiveCardIndex(){
        int condition = 0;
        for (int i = 0; i < cards.length; i++){
            if (cards[i].isActive())
                condition = i;
        }
        return condition;
    }

    public void dispose() {
        for (NumberCard card : cards)
            card.dispose();
    }
}
