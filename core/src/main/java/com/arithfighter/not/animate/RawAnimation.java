package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;

public class RawAnimation{
    private final AnimationProcessor processor;
    private final TimeHandler timeHandler;
    private Point drawPoint;

    public RawAnimation(Texture spriteSheet, int cols, int rows) {
        processor = new AnimationProcessor(spriteSheet, cols, rows);

        timeHandler = new TimeHandler();
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public AnimationProcessor getProcessor() {
        return processor;
    }

    public TimeHandler getTimeHandler() {
        return timeHandler;
    }

    public Point getDrawPoint() {
        return drawPoint;
    }

    public void handleAnimation() {
        timeHandler.updatePastedTime();

        processor.setPoint(drawPoint);
        processor.draw(drawPoint.getX(), drawPoint.getY());
    }

    public void resetTimeAndAnimation() {
        timeHandler.resetPastedTime();
        processor.init();
    }
}