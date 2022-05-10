package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.TimeLimitedAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final GeckoAction[] geckoActions;
    private SpriteBatch batch;

    public GeckoAnimate(Texture[] spriteSheets) {
        geckoActions = new GeckoAction[]{
                new GeckoBlink(spriteSheets),
                new GeckoSwing(spriteSheets),
                new GeckoEating(spriteSheets)
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

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void blink() {
        geckoActions[0].getAnimation().draw(batch);
    }

    public void swing(){
        geckoActions[1].getAnimation().draw(batch);
    }

    public void eat(){
        geckoActions[2].getAnimation().draw(batch);
    }

    public void init(){
        for (GeckoAction ga:geckoActions)
            ga.getAnimation().init();
    }
}

interface GeckoAction {
    TimeLimitedAnimation getAnimation();
}

class GeckoBlink implements GeckoAction{
    private final TimeLimitedAnimation geckoBlink;

    public GeckoBlink(Texture[] spriteSheets){
        geckoBlink = new TimeLimitedAnimation(spriteSheets[3], 2, 3);
        geckoBlink.setDrawTime(1);
        geckoBlink.setSpeed(0.08f);
    }

    public TimeLimitedAnimation getAnimation() {
        return geckoBlink;
    }
}

class GeckoSwing implements GeckoAction{
    private final TimeLimitedAnimation geckoSwing;

    public GeckoSwing(Texture[] spriteSheets){
        geckoSwing = new TimeLimitedAnimation(spriteSheets[4], 2, 4);
        geckoSwing.setDrawTime(1.2f);
        geckoSwing.setSpeed(0.08f);
    }

    public TimeLimitedAnimation getAnimation() {
        return geckoSwing;
    }
}

class GeckoEating implements GeckoAction{
    private final TimeLimitedAnimation geckoEating;

    public GeckoEating(Texture[] spriteSheets){
        geckoEating = new TimeLimitedAnimation(spriteSheets[5], 2, 4);
        geckoEating.setDrawTime(1.2f);
        geckoEating.setSpeed(0.08f);
    }

    @Override
    public TimeLimitedAnimation getAnimation() {
        return geckoEating;
    }
}