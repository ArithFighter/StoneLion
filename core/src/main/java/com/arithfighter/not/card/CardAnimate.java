package com.arithfighter.not.card;

import com.arithfighter.not.animate.VisualEffect;

public class CardAnimate{
    private final VisualEffect[] visualEffects;
    
    public CardAnimate(VisualEffect[] visualEffects) {
        this.visualEffects = visualEffects;
    }
    
    public VisualEffect getCardReset(){
        return visualEffects[0];
    }

    public VisualEffect getCardFadeOut(){
        return visualEffects[1];
    }
}
