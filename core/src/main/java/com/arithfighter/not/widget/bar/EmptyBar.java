package com.arithfighter.not.widget.bar;

import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar{
    private final VisibleWidget widget;

    public EmptyBar(Texture texture){
        widget = new SpriteWidget(texture,8,23);
    }

    public float getWidth(){
        return widget.getWidget().getWidth();
    }

    public float getHeight(){
        return widget.getWidget().getHeight();
    }

    public void setPosition(float x, float y){
        widget.setPosition(x,y);
    }

    public void draw(SpriteBatch batch) {
        widget.draw(batch);
    }
}
