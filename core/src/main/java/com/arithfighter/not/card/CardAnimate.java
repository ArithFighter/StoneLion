package com.arithfighter.not.card;

import com.arithfighter.not.animate.VisualAnimatable;
import com.arithfighter.not.animate.VisualEffect;

public class CardAnimate{
    private final VisualAnimatable[] visualEffects;

    public CardAnimate(VisualAnimatable[] visualEffects) {
        this.visualEffects = visualEffects;
    }

    public VisualEffect[] getVisualEffects() {
        VisualEffect[] ves = new VisualEffect[visualEffects.length];

        for (int i = 0; i< ves.length;i++)
            ves[i] = visualEffects[i].getVisualEffect();

        return ves;
    }

    public VisualEffect getCardReset(){
        return visualEffects[0].getVisualEffect();
    }

    public VisualEffect getCardFadeOut(){
        return visualEffects[1].getVisualEffect();
    }
}
