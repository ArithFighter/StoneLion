package com.arithfighter.not.widget.a1;

import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mask {
    private final VisibleWidget widget;

    public Mask(Texture texture, float scale){
        widget = new SpriteWidget(texture, scale);
    }

    public void setPosition(float x, float y){
        widget.setPosition(x,y);
    }

    public float getWidth(){
        return widget.getWidget().getWidth();
    }

    public float getHeight(){
        return widget.getWidget().getHeight();
    }

    public void draw(SpriteBatch batch){
        widget.getSprite().setColor(Color.BLACK);
        widget.draw(batch);
    }

    public void debug(SpriteBatch batch){
        widget.getSprite().setColor(Color.GREEN);
        widget.draw(batch);
    }
}
