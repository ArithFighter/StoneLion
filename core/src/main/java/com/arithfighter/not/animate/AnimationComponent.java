package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;

public class AnimationComponent {
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
