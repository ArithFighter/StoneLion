package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid{
    Sprite grid;
    private final SpriteWidget widget;

    public BarGrid(Texture texture){
        widget = new SpriteWidget();
        widget.configWidget(texture, 0.8f);

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
    }

    public float getWidth(){
        return widget.width;
    }

    public float getHeight(){
        return widget.height;
    }

    public void setPosition(float x, float y){
        widget.posX = x;
        widget.posY = y;
    }

    public void draw(SpriteBatch batch, float width) {
        widget.width = width;
        grid.setPosition(widget.posX,widget.posY);
        grid.setSize(widget.width, widget.height);
        grid.draw(batch);
    }
}
