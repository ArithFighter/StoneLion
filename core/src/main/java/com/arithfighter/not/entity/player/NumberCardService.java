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

    public int getCardNumber() {
        return cards[getActiveCardIndex()].getNumber();
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

class CharacterCardTexturePath {
    private final String[] snakeCardSet = {
            "cards/Number-2.png",
            "cards/Number-3.png",
            "cards/Number-9.png",
            "cards/Number-re0.png"
    };
    private final String[] craneCardSet = {
            "cards/Number-neg1.png",
            "cards/Number-3.png",
            "cards/Number-8.png",
            "cards/Number-re0.png"
    };
    private final String[] dogCardSet = {
            "cards/Number-2.png",
            "cards/Number-3.png",
            "cards/Number-5.png",
            "cards/Number-re12.png"
    };
    private final String[] catCardSet = {
            "cards/Number-neg1.png",
            "cards/Number-2.png",
            "cards/Number-7.png",
            "cards/Number-re15.png"
    };
    private final String[] fishCardSet = {
            "cards/Number-neg7.png",
            "cards/Number-neg3.png",
            "cards/Number-neg1.png",
            "cards/Number-max.png"
    };

    public String[] getSnakeCardSet() {
        return snakeCardSet;
    }

    public String[] getCraneCardSet() {
        return craneCardSet;
    }

    public String[] getDogCardSet() {
        return dogCardSet;
    }

    public String[] getCatCardSet() {
        return catCardSet;
    }

    public String[] getFishCardSet() {
        return fishCardSet;
    }
}