package com.arithfighter.ccg.card;

import com.arithfighter.ccg.widget.Point;

public class RawCard {
    private Point initPoint;
    private Point point;
    private float width, height;

    public void setInitPoint(Point initPoint) {
        this.initPoint = initPoint;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Point getInitPoint() {
        return initPoint;
    }

    public Point getPoint() {
        return point;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
