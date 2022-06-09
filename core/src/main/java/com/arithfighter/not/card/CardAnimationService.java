package com.arithfighter.not.card;

import com.arithfighter.not.animate.VisualAnimatable;
import com.arithfighter.not.animate.VisualEffect;
import com.arithfighter.not.animate.VisualEffectService;
import com.badlogic.gdx.graphics.Texture;

public class CardAnimationService {
    private final VisualAnimatable[] visualEffects;

    public CardAnimationService(Texture[] spriteSheets){
        visualEffects = new VisualAnimatable[]{
                new CardReset(spriteSheets[0]),
                new CardFadeOut(spriteSheets[1])
        };
    }

    public VisualAnimatable[] getVisualEffects() {
        return visualEffects;
    }
}

class CardFadeOut extends VisualEffectService {
    public CardFadeOut(Texture spriteSheet){
        setVisualEffect(new VisualEffect(spriteSheet,3,3));
        getVisualEffect().setFrameDuration(0.08f);
        getVisualEffect().setDuration(0.4f);
    }
}

class CardReset extends VisualEffectService{
    public CardReset(Texture spriteSheet){
        setVisualEffect(new VisualEffect(spriteSheet,3,3));
        getVisualEffect().setFrameDuration(0.08f);
        getVisualEffect().setDuration(0.48f);
    }
}