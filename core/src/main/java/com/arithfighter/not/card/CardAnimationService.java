package com.arithfighter.not.card;

import com.arithfighter.not.animate.se.SpecialAnimatable;
import com.arithfighter.not.animate.se.SpecialEffect;
import com.arithfighter.not.animate.se.SpecialEffectService;
import com.badlogic.gdx.graphics.Texture;

public class CardAnimationService {
    private final SpecialAnimatable[] visualEffects;

    public CardAnimationService(Texture[] spriteSheets){
        visualEffects = new SpecialAnimatable[]{
                new CardReset(spriteSheets[0]),
                new CardFadeOut(spriteSheets[1])
        };
    }

    public SpecialAnimatable[] getVisualEffects() {
        return visualEffects;
    }
}

class CardFadeOut extends SpecialEffectService {
    public CardFadeOut(Texture spriteSheet){
        setSpecialEffect(new SpecialEffect(spriteSheet,3,3));
        getSpecialEffect().setFrameDuration(0.08f);
        getSpecialEffect().setDuration(0.4f);
    }
}

class CardReset extends SpecialEffectService {
    public CardReset(Texture spriteSheet){
        setSpecialEffect(new SpecialEffect(spriteSheet,3,3));
        getSpecialEffect().setFrameDuration(0.08f);
        getSpecialEffect().setDuration(0.48f);
    }
}