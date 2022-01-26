package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    NumberCard[] cards;
    NumberCard num2Card, num3Card, num9Card, resetCard;
    float initX = WindowSetting.CENTER_X+WindowSetting.GRID_X*3;
    float initY = 0;
    float padding;

    public Hand(Texture texture) {
        num2Card = new NumberCard(initX, initY, texture, 2);

        padding = num2Card.getWidth()+WindowSetting.GRID_X;

        num3Card = new NumberCard(initX+padding, initY, texture, 3);
        num9Card = new NumberCard(initX+padding*2, initY, texture, 9);
        resetCard = new NumberCard(initX+padding*3, initY, texture, 0);

        cards = new NumberCard[]{num2Card, num3Card, num9Card, resetCard};
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
