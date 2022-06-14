package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;

public class AnimationModel {
    private float duration;
    private Point lastMousePoint;
    private Point drawPoint;

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setLastMousePoint(Point lastMousePoint) {
        this.lastMousePoint = lastMousePoint;
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public float getDuration() {
        return duration;
    }

    public Point getLastMousePoint() {
        return lastMousePoint;
    }

    public Point getDrawPoint() {
        return drawPoint;
    }
}
