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
