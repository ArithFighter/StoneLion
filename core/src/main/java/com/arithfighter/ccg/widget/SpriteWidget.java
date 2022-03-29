package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;

public class SpriteWidget extends Widget{
    public SpriteWidget(){
        setPoint(new Point(0,0));
    }

    public SpriteWidget(int fontSize){
        setPoint(new Point(0,0));
        setFontSize(fontSize);
    }

    public void setSize(Texture texture, float scale) {
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    private void configSize(float width, float height, float scale){
        setWidth(scale*width);
        setHeight(scale*height);
    }

    public float getCenterX(String content) {
        float midLength = getWidth() / 2 - content.length() * getFontSize() / 2f;

        return getPoint().getX() + midLength;
    }

    public float getCenterY() {
        float midHeight = (getHeight() + getFontSize()) / 2;

        return getPoint().getY() + midHeight;
    }
}

class Widget {
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
