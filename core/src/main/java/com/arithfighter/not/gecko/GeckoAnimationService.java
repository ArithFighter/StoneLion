package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.CharacterAnimatable;
import com.arithfighter.not.animate.CharacterAnimation;
import com.arithfighter.not.animate.CharacterAnimationEditor;
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

class Blink extends CharacterAnimationEditor {
    public Blink(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 3));
        getAnimation().setDuration(1);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Swing extends CharacterAnimationEditor {
    public Swing(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Eating extends CharacterAnimationEditor {
    public Eating(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.16f);
    }
}

class Licking extends CharacterAnimationEditor {
    public Licking(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(0.8f);
        getAnimation().setFrameDuration(0.1f);
    }
}

class FullEating extends CharacterAnimationEditor {
    public FullEating(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 4));
        getAnimation().setDuration(1.5f);
        getAnimation().setFrameDuration(0.18f);
    }
}

class Spitting extends CharacterAnimationEditor {
    public Spitting(Texture spriteSheet){
        setAnimation(new CharacterAnimation(spriteSheet, 2, 3));
        getAnimation().setDuration(1f);
        getAnimation().setFrameDuration(0.08f);
    }
}
