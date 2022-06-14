package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.character.CharacterAnimatable;
import com.arithfighter.not.animate.character.CharacterAnimation;
import com.arithfighter.not.animate.character.CharacterAnimationService;
import com.badlogic.gdx.graphics.Texture;

public class GeckoAnimationService {
    private final CharacterAnimatable[] characterAnimatable;

    public GeckoAnimationService(Texture[] spriteSheets) {
        characterAnimatable = new CharacterAnimatable[]{
                new Blink(spriteSheets[3]),
                new Swing(spriteSheets[4]),
                new Eating(spriteSheets[5]),
                new Licking(spriteSheets[6]),
                new FullEating(spriteSheets[8]),
                new Spitting(spriteSheets[9])
        };
    }

    public CharacterAnimatable[] getCharacterAnimatable() {
        return characterAnimatable;
    }
}

class Blink extends CharacterAnimationService {
    public Blink(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 3));
        getAnimation().setDuration(1);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Swing extends CharacterAnimationService {
    public Swing(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Eating extends CharacterAnimationService {
    public Eating(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.16f);
    }
}

class Licking extends CharacterAnimationService {
    public Licking(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(0.8f);
        getAnimation().setFrameDuration(0.1f);
    }
}

class FullEating extends CharacterAnimationService {
    public FullEating(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(1.5f);
        getAnimation().setFrameDuration(0.18f);
    }
}

class Spitting extends CharacterAnimationService {
    public Spitting(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 3));
        getAnimation().setDuration(1f);
        getAnimation().setFrameDuration(0.08f);
    }
}
