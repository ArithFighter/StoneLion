package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class LoopAnimation {
    private final AnimationProcessor processor;
    private final TimeHandler timeHandler;
    private Point drawPoint;
    private boolean isEnd = false;

    public LoopAnimation(Texture spriteSheet, int cols, int rows) {
        processor = new AnimationProcessor(spriteSheet, cols, rows);
        processor.setSpeed(0.08f);

        timeHandler = new TimeHandler();
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setScale(int scale){
        processor.setScale(scale);
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public void draw(SpriteBatch batch, int ratePerMin) {
        processor.setBatch(batch);

        long timeGap = 60000 / ratePerMin;
        long drawTime = 1000;
        if (TimeUtils.millis() % (timeGap+drawTime) < drawTime) {
                handleAnimation();
                isEnd = false;
        } else{
            isEnd = true;
            resetTimeAndAnimation();
        }
    }

    private void handleAnimation() {
        timeHandler.updatePastedTime();

        processor.setPoint(drawPoint);
        processor.draw(drawPoint.getX(), drawPoint.getY());
    }

    private void resetTimeAndAnimation() {
        timeHandler.resetPastedTime();
        processor.init();
    }
}
