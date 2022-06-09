package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeLimitedAnimation{
    private final AnimationProcessor processor;
    private final TimeHandler timeHandler;
    private final AnimationModel model;
    private boolean isEnd = true;

    public TimeLimitedAnimation(Texture spriteSheet, int cols, int rows) {
        processor = new AnimationProcessor(spriteSheet, cols, rows);

        model = new AnimationModel();

        timeHandler = new TimeHandler();
    }

    public void setDrawPoint(Point point){
        model.setDrawPoint(point);
    }

    public void setFrameDuration(float perSec) {
        processor.setFrameDuration(perSec);
    }

    public void setDuration(float second) {
        model.setDuration(second);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setScale(int scale){
        processor.setScale(scale);
    }

    public void draw(SpriteBatch batch) {
        processor.setBatch(batch);

        Point drawPoint = model.getDrawPoint();

        processor.setPoint(drawPoint);

        if (timeHandler.getPastedTime() < model.getDuration()) {
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

class AnimationModel{
    private Point drawPoint;
    private float duration;

    public Point getDrawPoint() {
        return drawPoint;
    }

    public float getDuration() {
        return duration;
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}