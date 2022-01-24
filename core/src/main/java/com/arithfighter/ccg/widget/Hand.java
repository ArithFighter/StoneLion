package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    Card whiteCard, greenCard, goldenCard, purpleCard;
    Card[] cards;
    float initX = WindowSetting.CENTER_X+WindowSetting.GRID_X*3;
    float initY = 0;
    float padding;
    int[] numberSet = {2, 3, 9, 0};

    public Hand(Texture texture) {
        whiteCard = new Card(initX, initY, Color.WHITE, texture, String.valueOf(numberSet[0]));
        padding = whiteCard.getWidth() + WindowSetting.GRID_X/1.2f;

        greenCard = new Card(initX + padding, initY, Color.GREEN, texture, String.valueOf(numberSet[1]));
        goldenCard = new Card(initX + padding * 2, initY, Color.GOLD, texture, String.valueOf(numberSet[2]));
        purpleCard = new Card(initX + padding * 3, initY, Color.PURPLE, texture, "RE");

        cards = new Card[]{whiteCard, greenCard, goldenCard, purpleCard};
    }

    public void draw(SpriteBatch batch) {
        for (Card card : cards)
            card.draw(batch);
    }

    public int getCardNumber(){
        return numberSet[getActiveCardIndex()];
    }

    public boolean isResetCard(){
        return getActiveCardIndex() == 3;
    }

    public void checkTouchingCard(float x, float y) {
        for (Card card : cards)
            card.checkTouchingCard(x, y);
    }

    public void checkActive(float x, float y) {
        for (Card card : cards)
            card.checkActive(x, y);
    }

    public void updateWhenDrag(float x, float y) {
        for (Card card : cards)
            card.updateWhenDrag(x, y);
    }

    public void resetHand() {
        for (Card card : cards)
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
        for (Card card : cards)
            card.dispose();
    }
}
