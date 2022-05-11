package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.TimeLimitedAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final GeckoAction[] geckoActions;

    public GeckoAnimate(Texture[] spriteSheets) {
        geckoActions = new GeckoAction[]{
                new Blink(spriteSheets),
                new Swing(spriteSheets),
                new Eating(spriteSheets),
                new Lick(spriteSheets)
        };
    }

    public void setScale(int scale){
        for (GeckoAction ga:geckoActions)
            ga.getAnimation().setScale(scale);
    }

    public void setDrawPoint(Point point){
        for (GeckoAction ga:geckoActions)
            ga.getAnimation().setDrawPoint(point);
    }

    public boolean isDefault(){
        boolean condition = false;
        int x = 0;

        for (GeckoAction ga:geckoActions){
            if (ga.getAnimation().isEnd())
                x++;
        }
        if (x == geckoActions.length)
            condition = true;

        return condition;
    }

    public void blink(SpriteBatch batch) {
        geckoActions[0].getAnimation().draw(batch);
    }

    public void swing(SpriteBatch batch){
        geckoActions[1].getAnimation().draw(batch);
    }

    public void eat(SpriteBatch batch){
        geckoActions[2].getAnimation().draw(batch);
    }

    public void lick(SpriteBatch batch){
        geckoActions[3].getAnimation().draw(batch);
    }

    public void init(){
        for (GeckoAction ga:geckoActions)
            ga.getAnimation().init();
    }
}

interface GeckoAction {
    TimeLimitedAnimation getAnimation();
}

class Animation implements GeckoAction{
    private TimeLimitedAnimation animation;

    public void setAnimation(TimeLimitedAnimation animation) {
        this.animation = animation;
    }

    @Override
    public TimeLimitedAnimation getAnimation() {
        return animation;
    }
}

class Blink extends Animation {

    public Blink(Texture[] spriteSheets){
        setAnimation(new TimeLimitedAnimation(spriteSheets[3], 2, 3));
        getAnimation().setDuration(1);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Swing extends Animation{

    public Swing(Texture[] spriteSheets){
        setAnimation(new TimeLimitedAnimation(spriteSheets[4], 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.08f);
    }
}

class Eating extends Animation{

    public Eating(Texture[] spriteSheets){
        setAnimation(new TimeLimitedAnimation(spriteSheets[5], 2, 4));
        getAnimation().setDuration(1.2f);
        getAnimation().setFrameDuration(0.16f);
    }
}

class Lick extends Animation{

    public Lick(Texture[] spriteSheets){
        setAnimation(new TimeLimitedAnimation(spriteSheets[6], 2, 4));
        getAnimation().setDuration(0.8f);
        getAnimation().setFrameDuration(0.1f);
    }
}