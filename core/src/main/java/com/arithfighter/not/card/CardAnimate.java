package com.arithfighter.not.card;

import com.arithfighter.not.animate.se.SpecialAnimatable;
import com.arithfighter.not.animate.se.SpecialEffect;

public class CardAnimate{
    private final SpecialAnimatable[] visualEffects;

    public CardAnimate(SpecialAnimatable[] visualEffects) {
        this.visualEffects = visualEffects;
    }

    public SpecialEffect[] getVisualEffects() {
        SpecialEffect[] ves = new SpecialEffect[visualEffects.length];

        for (int i = 0; i< ves.length;i++)
            ves[i] = visualEffects[i].getSpecialEffect();

        return ves;
    }

    public SpecialEffect getCardReset(){
        return visualEffects[0].getSpecialEffect();
    }

    public SpecialEffect getCardFadeOut(){
        return visualEffects[1].getSpecialEffect();
    }
}
