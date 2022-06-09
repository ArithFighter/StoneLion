package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.CharacterAnimatable;
import com.arithfighter.not.animate.CharacterAnimation;
import com.arithfighter.not.animate.CharacterAnimationModel;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final CharacterAnimatable[] characterAnimatable;

    public GeckoAnimate(CharacterAnimatable[] characterAnimatable) {
        this.characterAnimatable = characterAnimatable;
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