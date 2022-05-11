package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.TimeLimitedAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;

public class GeckoAnimate {
    private final GeckoAction[] geckoActions;

    public GeckoAnimate(Texture[] spriteSheets) {
        geckoActions = new GeckoAction[]{
                new GeckoBlink(spriteSheets),
                new GeckoSwing(spriteSheets),
                new GeckoEating(spriteSheets),
                new GeckoLick(spriteSheets)
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

    public TimeLimitedAnimation getBlink() {
        return geckoActions[0].getAnimation();
    }

    public TimeLimitedAnimation getSwing(){
        return geckoActions[1].getAnimation();
    }

    public TimeLimitedAnimation getEat(){
        return geckoActions[2].getAnimation();
    }

    public TimeLimitedAnimation getLick(){
        return geckoActions[3].getAnimation();
    }

    public void init(){
        for (GeckoAction ga:geckoActions)
            ga.getAnimation().init();
    }
}

interface GeckoAction {
    TimeLimitedAnimation getAnimation();
}

class GeckoAnimation implements GeckoAction{
    private TimeLimitedAnimation animation;

    public void setAnimation(TimeLimitedAnimation animation) {
        this.animation = animation;
    }

    @Override
    public TimeLimitedAnimation getAnimation() {
        return animation;
    }
}

class GeckoBlink extends GeckoAnimation{
    public GeckoBlink(Texture[] spriteSheets){
        setAnimation(new TimeLimitedAnimation(spriteSheets[3], 2, 3));
        getAnimation().setDuration(1);
        getAnimation().setFrameDuration(0.08f);
    }
}

class GeckoSwing implements GeckoAction{
    private final TimeLimitedAnimation geckoSwing;

    public GeckoSwing(Texture[] spriteSheets){
        geckoSwing = new TimeLimitedAnimation(spriteSheets[4], 2, 4);
        geckoSwing.setDuration(1.2f);
        geckoSwing.setFrameDuration(0.08f);
    }

    public TimeLimitedAnimation getAnimation() {
        return geckoSwing;
    }
}

class GeckoEating implements GeckoAction{
    private final TimeLimitedAnimation geckoEating;

    public GeckoEating(Texture[] spriteSheets){
        geckoEating = new TimeLimitedAnimation(spriteSheets[5], 2, 4);
        geckoEating.setDuration(1.2f);
        geckoEating.setFrameDuration(0.16f);
    }

    @Override
    public TimeLimitedAnimation getAnimation() {
        return geckoEating;
    }
}

class GeckoLick implements GeckoAction{
    private final TimeLimitedAnimation geckoLick;

    public GeckoLick(Texture[] spriteSheets){
        geckoLick = new TimeLimitedAnimation(spriteSheets[6], 2,4);
        geckoLick.setDuration(0.8f);
        geckoLick.setFrameDuration(0.1f);
    }
    @Override
    public TimeLimitedAnimation getAnimation() {
        return geckoLick;
    }
}