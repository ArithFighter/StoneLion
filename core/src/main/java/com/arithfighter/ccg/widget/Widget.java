package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;

public class Widget extends Shape {
    private int fontSize;

    public Widget(){
        setPoint(new Point(0,0));
    }

    public Widget(int fontSize){
        setPoint(new Point(0,0));
        this.fontSize = fontSize;
    }

    public void setSize(Texture texture, float scale) {
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    private void configSize(float width, float height, float scale){
        setWidth(scale*width);
        setHeight(scale*height);
    }

    public float getCenterX(String content) {
        float midLength = getWidth() / 2 - content.length() * fontSize / 2f;

        return getPoint().getX() + midLength;
    }

    public float getCenterY() {
        float midHeight = (getHeight() + fontSize) / 2;

        return getPoint().getY() + midHeight;
    }

    public int getFontSize(){
        return fontSize;
    }
}
