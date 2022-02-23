package com.arithfighter.ccg.component;

import com.arithfighter.ccg.SkillFlag;
import com.arithfighter.ccg.card.CardTexturesExtractor;
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
    CharacterList characterList;

    public Player(Texture[] textures, CharacterList character) {
        cardTexturesExtractor = new CardTexturesExtractor(textures);

        CharacterSetCollection csc = new CharacterSetCollection();

        int[] numberSet = csc.getCharacterSet(character);

        cardHand = new CardHand(numberSet, cardTexturesExtractor.getCardSet(character), character);

        cards = cardHand.getCards();

        characterList = character;
    }

    public void draw(SpriteBatch batch, SkillFlag isSkillActive) {
        if (isSkillActive == SkillFlag.ACTIVE){
            if (characterList == CharacterList.KNIGHT){
                for(int i = 1;i<5;i++)
                    cards[i].draw(batch);
            }
            else{
                for (int i = 0; i<4;i++)
                    cards[i].draw(batch);
            }
        }
        else
            for (int i = 0; i<4;i++)
                cards[i].draw(batch);
    }

    public int getCardNumber(SkillFlag isSkillActive){
        int cardNumber;

        if (isSkillActive == SkillFlag.ACTIVE){
            if (characterList == CharacterList.KNIGHT)
                cardNumber = useKnightSkill();
            else
                cardNumber = cards[getActiveCardIndex()].getNumber();
        }
        else cardNumber = cards[getActiveCardIndex()].getNumber();

        return cardNumber;
    }

    private int useKnightSkill(){
        int cardNumber;

        if (getActiveCardIndex() == 4)
            cardNumber = cards[getActiveCardIndex()].getNumber()-1;
        else
            cardNumber = cards[getActiveCardIndex()].getNumber();

        return cardNumber;
    }

    public boolean isResetCard(){
        return getActiveCardIndex() == 3;
    }

    public void checkTouchingCard(float x, float y) {
        for (NumberCard card : cards)
            card.checkTouchingCard(x, y);
    }

    public void activateCard(float x, float y) {
        for (NumberCard card : cards)
            card.activateCard(x, y);
    }

    public void updateWhenDrag(float x, float y) {
        for (NumberCard card : cards)
            card.updateWhenDrag(x, y);
    }

    public void initCardsPosition() {
        for (NumberCard card : cards)
            card.initCard();
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
