package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;

public class Widget {
    private Point point;
    private int fontSize;
    private final Shape shape;

    public Widget(){
        shape = new Shape();
        setPoint(new Point(0,0));
    }

    public Widget(int fontSize){
        shape = new Shape();
        setPoint(new Point(0,0));
        this.fontSize = fontSize;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setSize(Texture texture, float scale) {
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    public float getWidth(){
        return shape.getWidth();
    }

    public float getHeight(){
        return shape.getHeight();
    }

    public void setWidth(float width){
        shape.setWidth(width);
    }

    private void configSize(float width, float height, float scale){
        shape.setWidth(scale*width);
        shape.setHeight(scale*height);
    }

    public float getCenterX(String content) {
        float midLength = shape.getWidth() / 2 - content.length() * fontSize / 2f;

        return point.getX() + midLength;
    }

    public float getCenterY() {
        float midHeight = (shape.getHeight() + fontSize) / 2;

        return point.getY() + midHeight;
    }

    public int getFontSize(){
        return fontSize;
    }
}
