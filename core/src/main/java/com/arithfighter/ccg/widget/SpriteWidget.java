package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;

public class SpriteWidget extends Widget{
    public void configWidget(Texture texture, float scale) {
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    private void configSize(float width, float height, float scale){
        this.width = scale*width;
        this.height = scale*height;
    }

    public float getCenterX(String content) {
        float midLength = width / 2 - content.length() * fontSize / 2f;

        return point.getX() + midLength;
    }

    public float getCenterY() {
        float midHeight = (height + fontSize) / 2;

        return point.getY() + midHeight;
    }
}
