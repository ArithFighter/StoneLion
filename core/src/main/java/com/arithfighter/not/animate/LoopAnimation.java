package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class LoopAnimation{
    private final RawAnimation rawAnimation;
    private int drawTime;
    private boolean isEnd = false;
    private int ratePerMin;

    public LoopAnimation(Texture spriteSheet, int cols, int rows) {
        rawAnimation = new RawAnimation(spriteSheet, cols, rows);

        rawAnimation.getProcessor().setSpeed(0.08f);
    }

    public void setRatePerMin(int ratePerMin) {
        this.ratePerMin = ratePerMin;
    }

    public void setDrawTime(int millisecond) {
        this.drawTime = millisecond;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setScale(int scale){
        rawAnimation.getProcessor().setScale(scale);
    }

    public void setDrawPoint(Point point){
        rawAnimation.setDrawPoint(point);
    }

    public void draw(SpriteBatch batch) {
        rawAnimation.getProcessor().setBatch(batch);

        long timeGap = 60000 / ratePerMin;

        if (TimeUtils.millis() % (timeGap+drawTime) < drawTime) {
                rawAnimation.handleAnimation();
                isEnd = false;
        } else{
            isEnd = true;
            rawAnimation.resetTimeAndAnimation();
        }
    }
}
