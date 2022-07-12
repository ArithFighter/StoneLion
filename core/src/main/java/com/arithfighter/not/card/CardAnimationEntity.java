package com.arithfighter.not.card;

import com.arithfighter.not.animate.se.SpecialAnimatable;
import com.arithfighter.not.animate.se.SpecialEffect;

public class CardAnimationEntity {
    private final SpecialAnimatable[] specialEffects;

    public CardAnimationEntity(SpecialAnimatable[] specialEffects) {
        this.specialEffects = specialEffects;
    }

    public SpecialEffect[] getSpecialEffects() {
        SpecialEffect[] ves = new SpecialEffect[specialEffects.length];

        for (int i = 0; i< ves.length;i++)
            ves[i] = specialEffects[i].getSpecialEffect();

        return ves;
    }

    public boolean isAllNotStart(){
        boolean condition = false;
        int x = 0;

        for (SpecialAnimatable sa: specialEffects){
            if (!sa.getSpecialEffect().isStart())
                x++;
        }
        if (x == specialEffects.length)
            condition = true;

        return condition;
    }

    public void init(){
        for (SpecialAnimatable sa: specialEffects)
            sa.getSpecialEffect().init();
    }

    public SpecialEffect getCardReset(){
        return specialEffects[0].getSpecialEffect();
    }

    public SpecialEffect getCardFadeOut(){
        return specialEffects[1].getSpecialEffect();
    }
}
