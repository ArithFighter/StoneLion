package com.arithfighter.not.gecko;

import com.arithfighter.not.animate.LoopAnimation;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoAnimate {
    private final LoopAnimation geckoBlink;
    private SpriteBatch batch;

    public GeckoAnimate(Texture[] spriteSheets, int scale, Point point) {
        geckoBlink = new LoopAnimation(spriteSheets[3], 2, 3);
        geckoBlink.setScale(scale);
        geckoBlink.setDrawPoint(point);
    }

    public boolean isDrawing(){
        return geckoBlink.isDrawing();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void blink() {
        geckoBlink.draw(batch, 30);
    }
}
