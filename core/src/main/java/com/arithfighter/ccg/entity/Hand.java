package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.WindowSetting;
import com.arithfighter.ccg.card.NumberCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    private final NumberCard[] cards;
    private final CardTexturesExtractor texturesExtractor;

    public Hand(Texture[] textures, CharacterList character) {
        texturesExtractor = new CardTexturesExtractor(textures);

        cards = new NumberCard[texturesExtractor.getCardTextures(character).length];

        createCardList(character);
    }

    private void createCardList(CharacterList character) {
        float initX = WindowSetting.CENTER_X + WindowSetting.GRID_X * 1.2f;
        float initY = -WindowSetting.GRID_Y;
        Texture[] cardSet = texturesExtractor.getCardTextures(character);

        for (int i = 0; i < cards.length; i++)
            cards[i] = new NumberCard(
                    initX + i * getPadding(cardSet),
                    initY,
                    cardSet[i],
                    character.numberSet[i]
            );
    }

    private float getPadding(Texture[] textures) {
        NumberCard sample = new NumberCard(0, 0, textures[0], 0);
        return sample.getWidth() + WindowSetting.GRID_X * 0.8f;
    }

    public void draw(SpriteBatch batch) {
        for (NumberCard card : cards)
            card.draw(batch);
    }

    public int getCardNumber() {
        return cards[getActiveCardIndex()].getNumber();
    }

    public boolean isResettingCard() {
        return getActiveCardIndex() == cards.length-1;
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

    private int getActiveCardIndex() {
        int index = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].isActive())
                index = i;
        }
        return index;
    }

    public void dispose() {
        texturesExtractor.dispose();
    }
}

class CardTexturesExtractor {
    private final Texture[] cardTextures;
    private final CharacterList[] characters = CharacterList.values();
    private final int quantity = characters[0].numberSet.length;

    public CardTexturesExtractor(Texture[] cardTextures) {
        this.cardTextures = cardTextures;
    }

    public Texture[] getCardTextures(CharacterList player) {
        Texture[] cardSet = new Texture[quantity];

        for (CharacterList character : characters)
            if (player == character) {
                for (int j = 0; j < quantity; j++)
                    cardSet[j] = cardTextures[character.textureMap[j]];
            }

        return cardSet;
    }

    public void dispose() {
        for (Texture texture : cardTextures)
            texture.dispose();
    }
}
