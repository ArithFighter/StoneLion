package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.LoopAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final GeckoLoop geckoBlink;
    private final GeckoLoop geckoSwing;
    private SpriteBatch batch;

    public GeckoAnimate(Texture[] spriteSheets) {
        geckoBlink = new GeckoBlink(spriteSheets);
        geckoSwing = new GeckoSwing(spriteSheets);
    }

    public void setScale(int scale){
        geckoBlink.getAnimation().setScale(scale);
        geckoSwing.getAnimation().setScale(scale);
    }

    public void setDrawPoint(Point point){
        geckoBlink.getAnimation().setDrawPoint(point);
        geckoSwing.getAnimation().setDrawPoint(point);
    }

    public boolean isDefault(){
        return geckoBlink.getAnimation().isEnd()&&geckoSwing.getAnimation().isEnd();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void blink() {
        geckoBlink.getAnimation().draw(batch);
    }

    public void swing(){
        geckoSwing.getAnimation().draw(batch);
    }
}

interface GeckoLoop {
    LoopAnimation getAnimation();
}

class GeckoBlink implements GeckoLoop {
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

class GeckoSwing implements GeckoLoop{
    private final LoopAnimation geckoSwing;

    public GeckoSwing(Texture[] spriteSheets){
        geckoSwing = new LoopAnimation(spriteSheets[4], 2, 4);
        geckoSwing.setDrawTime(1200);
        geckoSwing.setRatePerMin(15);
    }

    @Override
    public LoopAnimation getAnimation() {
        return geckoSwing;
    }
}