package com.arithfighter.ccg.widget.bar;

import com.arithfighter.ccg.widget.FlexibleWidget;
import com.arithfighter.ccg.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid{
    private final FlexibleWidget widget;

    public BarGrid(Texture texture){
        widget = new SpriteWidget(texture, 0.8f);
        widget.getSprite().setColor(Color.SKY);
    }

    public float getWidth(){
        return widget.getWidget().getWidth();
    }

    public float getHeight(){
        return widget.getWidget().getHeight();
    }

    public void setPosition(float x, float y){
        widget.setPosition(x, y);
    }

    public void updateWidth(float width){
        widget.updateWidth(width);
    }

    public void draw(SpriteBatch batch) {
        widget.draw(batch);
    }
}
