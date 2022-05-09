package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeLimitedAnimation{
    private final RawAnimation rawAnimation;
    private int drawTime;
    private boolean isEnd = false;

    public TimeLimitedAnimation(Texture spriteSheet, int cols, int rows) {
        rawAnimation = new RawAnimation(spriteSheet, cols, rows);
    }

    public void setDrawPoint(Point point){
        rawAnimation.setDrawPoint(point);
    }

    public void setSpeed(float speed) {
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
        }else
            isEnd = true;
    }

    public void init(){
        isEnd = false;
        rawAnimation.resetTimeAndAnimation();
    }
}
