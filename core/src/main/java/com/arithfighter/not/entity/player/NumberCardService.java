package com.arithfighter.not.entity.player;

import com.arithfighter.not.card.NumberCard;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberCardService {
    private final NumberCard[] cards;
    private final float padding;

    public NumberCardService(Texture[] textures, CharacterList character) {
        cards = new NumberCard[character.getTextureMap().length];

        CardTexturesExtractor texturesExtractor = new CardTexturesExtractor(textures);
        Texture[] cardSet = texturesExtractor.getCardTextures(character);

        int scale = 2;
        padding = textures[0].getWidth()* scale + 18;

        for (int i = 0; i < cards.length; i++){
            cards[i] = new NumberCard(cardSet[i], scale);
            cards[i].setNumber(character.getNumberSet()[i]);
        }
    }

    public void setInitPoint(Point point){
        for (int i = 0; i < cards.length; i++)
            cards[i].getInitPoint().set(point.getX()+ i * padding, point.getY());
    }

    public void draw(SpriteBatch batch) {
        for (NumberCard card : cards)
            card.draw(batch);
    }

    public NumberCard getActiveCard(){
        return cards[getActiveCardIndex()];
    }

    public boolean isResettingCard() {
        return getActiveCardIndex() == cards.length-1;
    }

    public NumberCard[] getCards(){
        return cards;
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
}

class CardTexturesExtractor {
    private final Texture[] cardTextures;
    private final CharacterList[] characters = CharacterList.values();
    private final int quantity = characters[0].getTextureMap().length;

    public CardTexturesExtractor(Texture[] cardTextures) {
        this.cardTextures = cardTextures;
    }

    public Texture[] getCardTextures(CharacterList player) {
        Texture[] cardSet = new Texture[quantity];

        for (CharacterList character : characters)
            if (player == character) {
                for (int j = 0; j < quantity; j++)
                    cardSet[j] = cardTextures[character.getTextureMap()[j]];
            }

        return cardSet;
    }
}