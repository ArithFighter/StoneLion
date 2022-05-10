package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeLimitedAnimation{
    private final AnimationProcessor processor;
    private Point drawPoint;
    private final TimeHandler timeHandler;
    private float duration;
    private boolean isEnd = true;

    public TimeLimitedAnimation(Texture spriteSheet, int cols, int rows) {
        processor = new AnimationProcessor(spriteSheet, cols, rows);

        timeHandler = new TimeHandler();
    }

    public void setDrawPoint(Point point){
        drawPoint = point;
    }

    public void setFrameDuration(float perSec) {
        processor.setFrameDuration(perSec);
    }

    public void setDuration(float second) {
        this.duration = second;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setScale(int scale){
        processor.setScale(scale);
    }

    public void draw(SpriteBatch batch) {
        processor.setBatch(batch);

        processor.setPoint(drawPoint);

        if (timeHandler.getPastedTime() < duration) {
            timeHandler.updatePastedTime();
            processor.draw(drawPoint.getX(), drawPoint.getY());
            isEnd = false;
        }else
            isEnd = true;
    }

    public void init(){
        isEnd = true;
        timeHandler.resetPastedTime();
        processor.init();
    }
}
