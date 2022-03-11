package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid{
    Sprite grid;
    private final SpriteWidget widget;
    private final Point point;

    public BarGrid(Texture texture){
        widget = new SpriteWidget();
        widget.setSize(texture, 0.8f);
        point = widget.point;

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
        point.setX(x);
        point.setY(y);
    }

    public void draw(SpriteBatch batch, float width) {
        widget.width = width;
        grid.setPosition(point.getX(), point.getY());
        grid.setSize(widget.width, widget.height);
        grid.draw(batch);
    }
}
