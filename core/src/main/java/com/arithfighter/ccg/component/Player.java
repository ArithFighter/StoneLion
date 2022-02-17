package com.arithfighter.ccg.component;

import com.arithfighter.ccg.CardTexturesExtractor;
import com.arithfighter.ccg.CharacterList;
import com.arithfighter.ccg.CharacterSetCollection;
import com.arithfighter.ccg.card.NumberCard;
import com.arithfighter.ccg.card.CardHand;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    NumberCard[] cards;
    CardHand cardHand;
    CardTexturesExtractor cardTexturesExtractor;

    public Player(Texture[] textures, CharacterList character) {
        cardTexturesExtractor = new CardTexturesExtractor(textures);

        CharacterSetCollection csc = new CharacterSetCollection();

        int[] numberSet = csc.getCharacterSet(character);

        cardHand = new CardHand(numberSet, cardTexturesExtractor.getCardSet(character));

        cards = cardHand.getCards();
    }

    public void draw(SpriteBatch batch) {
        for (NumberCard card : cards)
            card.draw(batch);
    }

    public int getCardNumber(){
        return cards[getActiveCardIndex()].getNumber();
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
            card.resetPosition();
    }

    public boolean isCardActive() {
        return cards[getActiveCardIndex()].isActive();
    }

    private int getActiveCardIndex(){
        int index = 0;
        for (int i = 0; i < cards.length; i++){
            if (cards[i].isActive())
                index = i;
        }
        return index;
    }

    public void dispose(){
        cardTexturesExtractor.dispose();
    }
}
