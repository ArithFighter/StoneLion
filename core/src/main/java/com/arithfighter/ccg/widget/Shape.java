package com.arithfighter.ccg.widget;

public class Shape {
    private Point point;
    private float width, height;

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
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
