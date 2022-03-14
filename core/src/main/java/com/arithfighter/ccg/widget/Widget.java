package com.arithfighter.ccg.widget;

public class Widget {
    private Point point;
    private float width, height;
    private int fontSize;

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
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

    public int getFontSize() {
        return fontSize;
    }
}
