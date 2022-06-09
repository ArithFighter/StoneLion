package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.CharacterAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final CharacterAnimatable[] characterAnimatable;

    public GeckoAnimate(Texture[] spriteSheets) {
        characterAnimatable = new CharacterAnimatable[]{
                new Blink(spriteSheets),
                new Swing(spriteSheets),
                new Eating(spriteSheets),
                new Licking(spriteSheets),
                new FullEating(spriteSheets),
                new Spitting(spriteSheets)
        };
    }

    public void setScale(int scale){
        for (CharacterAnimatable ga: characterAnimatable)
            ga.getAnimation().setScale(scale);
    }

    public void setDrawPoint(Point point){
        for (CharacterAnimatable ga: characterAnimatable)
            ga.getAnimation().setDrawPoint(point);
    }

    public boolean isAllActionEnd(){
        boolean condition = false;
        int x = 0;

        for (CharacterAnimatable ga: characterAnimatable){
            if (ga.getAnimation().isEnd())
                x++;
        }
        if (x == characterAnimatable.length)
            condition = true;

        return condition;
    }

    public void blink(SpriteBatch batch) {
        characterAnimatable[0].getAnimation().draw(batch);
    }

    public void swing(SpriteBatch batch){
        characterAnimatable[1].getAnimation().draw(batch);
    }

    public void eat(SpriteBatch batch){
        characterAnimatable[2].getAnimation().draw(batch);
    }

    public void lick(SpriteBatch batch){
        characterAnimatable[3].getAnimation().draw(batch);
    }

    public void eatWhenFull(SpriteBatch batch){
        characterAnimatable[4].getAnimation().draw(batch);
    }

    public void spit(SpriteBatch batch){
        characterAnimatable[5].getAnimation().draw(batch);
    }

    public void init(){
        for (CharacterAnimatable ga: characterAnimatable)
            ga.getAnimation().init();
    }
}

class GeckoAnimationService{
    private final CharacterAnimatable[] characterAnimatable;

    public GeckoAnimationService(Texture[] spriteSheets){
        characterAnimatable = new CharacterAnimatable[]{
                new Blink(spriteSheets),
                new Swing(spriteSheets),
                new Eating(spriteSheets),
                new Licking(spriteSheets),
                new FullEating(spriteSheets),
                new Spitting(spriteSheets)
        };
    }

    public CharacterAnimatable[] getCharacterAnimatable() {
        return characterAnimatable;
    }
}

interface CharacterAnimatable {
    CharacterAnimation getAnimation();
}

class CharacterAnimationModel implements CharacterAnimatable {
    private CharacterAnimation animation;

    public void setAnimation(CharacterAnimation animation) {
        this.animation = animation;
    }

    @Override
    public CharacterAnimation getAnimation() {
        return animation;
    }
}

class Blink extends CharacterAnimationModel {
    public Blink(Texture[] spriteSheets){
        setAnimation(new CharacterAnimation(spriteSheets[3], 2, 3));
        getAnimation().setDuration(1);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Swing extends CharacterAnimationModel {
    public Swing(Texture[] spriteSheets){
        setAnimation(new CharacterAnimation(spriteSheets[4], 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Eating extends CharacterAnimationModel {
    public Eating(Texture[] spriteSheets){
        setAnimation(new CharacterAnimation(spriteSheets[5], 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.16f);
    }
}

class Licking extends CharacterAnimationModel {
    public Licking(Texture[] spriteSheets){
        setAnimation(new CharacterAnimation(spriteSheets[6], 2, 4));
        getAnimation().setDuration(0.8f);
        getAnimation().setFrameDuration(0.1f);
    }
}

class FullEating extends CharacterAnimationModel {
    public FullEating(Texture[] spriteSheets){
        setAnimation(new CharacterAnimation(spriteSheets[8], 2, 4));
        getAnimation().setDuration(1.5f);
        getAnimation().setFrameDuration(0.18f);
    }
}

class Spitting extends CharacterAnimationModel {
    public Spitting(Texture[] spriteSheets){
        setAnimation(new CharacterAnimation(spriteSheets[9], 2, 3));
        getAnimation().setDuration(1f);
        getAnimation().setFrameDuration(0.08f);
    }
}