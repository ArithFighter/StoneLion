package com.arithfighter.not.animate;

import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeLimitedAnimation extends AnimationComponent{
    private int drawTime;
    private boolean isEnd = false;

    public TimeLimitedAnimation(Texture spriteSheet, int cols, int rows) {
        setProcessor(new AnimationProcessor(spriteSheet, cols, rows));

        setTimeHandler(new TimeHandler());
    }

    public void setSpeed(int speed) {
        getProcessor().setSpeed(speed);
    }

    public void setDrawTime(int second) {
        this.drawTime = second;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setScale(int scale){
        getProcessor().setScale(scale);
    }

    public void draw(SpriteBatch batch) {
        getProcessor().setBatch(batch);

        if (getTimeHandler().getPastedTime() < drawTime) {
                handleAnimation();
                isEnd = false;
        } else{
            isEnd = true;
            resetTimeAndAnimation();
        }
    }

    private void handleAnimation() {
        getTimeHandler().updatePastedTime();

        getProcessor().setPoint(getDrawPoint());
        getProcessor().draw(getDrawPoint().getX(), getDrawPoint().getY());
    }

    private void resetTimeAndAnimation() {
        getTimeHandler().resetPastedTime();
        getProcessor().init();
    }
}
