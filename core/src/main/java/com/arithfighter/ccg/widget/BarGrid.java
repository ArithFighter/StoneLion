package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid{
    Sprite grid;
    private final TextureWidget widget;

    public BarGrid(Texture texture, float initX, float initY){
        widget = new TextureWidget();
        widget.configWidget(texture, 0.8f);
        widget.posX = initX;
        widget.posY = initY;

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
        grid.setPosition(widget.posX, widget.posY);
        grid.setSize(widget.width, widget.height);
    }

    public float getWidth(){
        return widget.width;
    }

    public float getHeight(){
        return widget.height;
    }

    public void setPos(float x, float y){
        widget.posX = x;
        widget.posY = y;
        grid.setPosition(x,y);
    }

    public void draw(SpriteBatch batch, float width) {
        widget.width = width;
        grid.setSize(widget.width, widget.height);
        grid.draw(batch);
    }
}
