package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.character.CharacterAnimatable;
import com.arithfighter.not.animate.character.CharacterAnimateController;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate extends CharacterAnimateController {
    public GeckoAnimate(CharacterAnimatable[] characterAnimatable) {
        super(characterAnimatable);
    }

    public void blink(SpriteBatch batch) {
        getCharacterAnimatable()[0].getAnimation().draw(batch);
    }

    public void swing(SpriteBatch batch){
        getCharacterAnimatable()[1].getAnimation().draw(batch);
    }

    public void eat(SpriteBatch batch){
        getCharacterAnimatable()[2].getAnimation().draw(batch);
    }

    public void lick(SpriteBatch batch){
        getCharacterAnimatable()[3].getAnimation().draw(batch);
    }

    public void eatWhenFull(SpriteBatch batch){
        getCharacterAnimatable()[4].getAnimation().draw(batch);
    }

    public void spit(SpriteBatch batch){
        getCharacterAnimatable()[5].getAnimation().draw(batch);
    }
}