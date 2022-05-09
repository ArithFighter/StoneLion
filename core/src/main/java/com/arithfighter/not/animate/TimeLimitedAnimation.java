package com.arithfighter.not.animate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeLimitedAnimation{
    private final RawAnimation rawAnimation;
    private int drawTime;
    private boolean isEnd = false;

    public TimeLimitedAnimation(Texture spriteSheet, int cols, int rows) {
        rawAnimation = new RawAnimation(spriteSheet, cols, rows);
    }

    public void setSpeed(int speed) {
        rawAnimation.getProcessor().setSpeed(speed);
    }

    public void setDrawTime(int second) {
        this.drawTime = second;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setScale(int scale){
        rawAnimation.getProcessor().setScale(scale);
    }

    public void draw(SpriteBatch batch) {
        rawAnimation.getProcessor().setBatch(batch);

        if (rawAnimation.getTimeHandler().getPastedTime() < drawTime) {
                rawAnimation.handleAnimation();
                isEnd = false;
        } else{
            isEnd = true;
            rawAnimation.resetTimeAndAnimation();
        }
    }
}
