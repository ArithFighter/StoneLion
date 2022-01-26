package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    NumberCard[] cards;
    NumberCard num2Card;
    float initX = WindowSetting.CENTER_X+WindowSetting.GRID_X*3;
    float initY = 0;
    float padding;

    public Hand(Texture texture) {
        num2Card = new NumberCard(initX, initY, texture, 2);

        cards = new NumberCard[]{num2Card};
    }

    public void draw(SpriteBatch batch) {
        for (NumberCard card : cards)
            card.draw(batch);
    }

    public int getCardNumber(){
        return cards[getActiveCardIndex()].getNumber();
    }

    public boolean isResetCard(){
        return getCardNumber() == 0;
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
