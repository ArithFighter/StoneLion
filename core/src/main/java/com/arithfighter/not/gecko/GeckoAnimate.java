package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.LoopAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final GeckoAnimationFactory geckoBlink;
    private SpriteBatch batch;

    public GeckoAnimate(Texture[] spriteSheets) {
        geckoBlink = new GeckoBlink(spriteSheets);
    }

    public void setScale(int scale){
        geckoBlink.getAnimation().setScale(scale);
    }

    public void setDrawPoint(Point point){
        geckoBlink.getAnimation().setDrawPoint(point);
    }

    public boolean isDefault(){
        return geckoBlink.getAnimation().isEnd();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void blink() {
        geckoBlink.getAnimation().draw(batch);
    }
}

interface GeckoAnimationFactory{
    LoopAnimation getAnimation();
}

class GeckoBlink implements GeckoAnimationFactory{
    private final LoopAnimation geckoBlink;

    public GeckoBlink(Texture[] spriteSheets){
        geckoBlink = new LoopAnimation(spriteSheets[3], 2, 3);
        geckoBlink.setDrawTime(1000);
        geckoBlink.setRatePerMin(20);
    }

    @Override
    public LoopAnimation getAnimation() {
        return geckoBlink;
    }
}