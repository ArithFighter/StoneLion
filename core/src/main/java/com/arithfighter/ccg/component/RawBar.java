package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RawBar extends RawWidget{
    Sprite bar;

    public RawBar(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 8f);

        configFont(32);

        bar = new Sprite(texture);
        bar.setPosition(widgetX, widgetY);
        bar.setSize(widgetWidth, widgetHeight);
    }

    public void draw(SpriteBatch batch) {
        bar.draw(batch);
    }
}
