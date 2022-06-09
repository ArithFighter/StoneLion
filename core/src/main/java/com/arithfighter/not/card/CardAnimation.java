package com.arithfighter.not.card;

import com.arithfighter.not.animate.VisualEffect;
import com.badlogic.gdx.graphics.Texture;

public class CardAnimation {
}

class CardFadeOut{
    private final VisualEffect cardFadeOut;

    public CardFadeOut(Texture spriteSheet){
        cardFadeOut = new VisualEffect(spriteSheet,3,3);
        cardFadeOut.setScale(16);
        cardFadeOut.setFrameDuration(0.08f);
        cardFadeOut.setDuration(0.4f);
    }

    public VisualEffect getCardFadeOut() {
        return cardFadeOut;
    }
}

class CardReset{
    private final VisualEffect cardReset;

    public CardReset(Texture spriteSheet){
        cardReset = new VisualEffect(spriteSheet,3,3);
        cardReset.setScale(16);
        cardReset.setFrameDuration(0.08f);
        cardReset.setDuration(0.48f);
    }

    public VisualEffect getCardReset() {
        return cardReset;
    }
}