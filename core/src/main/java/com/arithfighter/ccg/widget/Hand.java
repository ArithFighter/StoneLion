package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.CharacterList;
import com.arithfighter.ccg.CharacterSetCollection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    NumberCard[] cards;
    NumberCardCollection numberCardCollection;

    public Hand(Texture texture, CharacterList character) {
        CharacterSetCollection csc = new CharacterSetCollection();

        int[] numberSet = csc.getCharacterSet(character);

        numberCardCollection = new NumberCardCollection(numberSet[0],numberSet[1],numberSet[2],texture);

        cards = numberCardCollection.getCards();
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
