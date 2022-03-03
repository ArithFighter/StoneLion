package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;

public class TextureWidget extends Widget{
    public void configWidget(Texture texture, float scale) {
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    private void configSize(float width, float height, float scale){
        this.width = scale*width;
        this.height = scale*height;
    }
}
