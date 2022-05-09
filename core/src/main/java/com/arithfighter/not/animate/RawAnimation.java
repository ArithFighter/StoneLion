package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;

public class RawAnimation extends AnimationComponent {
    public RawAnimation(Texture spriteSheet, int cols, int rows) {
        setProcessor(new AnimationProcessor(spriteSheet, cols, rows));

        setTimeHandler(new TimeHandler());
    }

    public void handleAnimation() {
        getTimeHandler().updatePastedTime();

        getProcessor().setPoint(getDrawPoint());
        getProcessor().draw(getDrawPoint().getX(), getDrawPoint().getY());
    }

    public void resetTimeAndAnimation() {
        getTimeHandler().resetPastedTime();
        getProcessor().init();
    }
}

class AnimationComponent {
    private AnimationProcessor processor;
    private TimeHandler timeHandler;
    private Point drawPoint;

    public void setProcessor(AnimationProcessor processor) {
        this.processor = processor;
    }

    public void setTimeHandler(TimeHandler timeHandler) {
        this.timeHandler = timeHandler;
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
}