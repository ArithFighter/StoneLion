package com.arithfighter.ccg.widget.bar;

import com.arithfighter.ccg.widget.Point;
import com.arithfighter.ccg.widget.Widget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid{
    final Sprite grid;
    private final Widget widget;
    private final Point point;

    public BarGrid(Texture texture){
        widget = new Widget();
        widget.setSize(texture, 0.8f);
        point = widget.getPoint();

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
    }

    public float getWidth(){
        return widget.getWidth();
    }

    public float getHeight(){
        return widget.getHeight();
    }

    public void setPosition(float x, float y){
        point.set(x, y);
    }

    public void updateWidth(float width){
        widget.setWidth(width);
    }

    public void draw(SpriteBatch batch) {
        grid.setPosition(point.getX(), point.getY());
        grid.setSize(widget.getWidth(), widget.getHeight());
        grid.draw(batch);
    }
}
