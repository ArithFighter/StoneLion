package com.arithfighter.ccg.widget.bar;

import com.arithfighter.ccg.widget.Point;
import com.arithfighter.ccg.widget.Widget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar{
    private final Sprite bar;
    private final Widget widget;
    private final Point point;

    public EmptyBar(Texture texture){
        widget = new Widget(23);
        widget.setSize(texture, 8f);
        point = widget.getPoint();

        bar = new Sprite(texture);
    }

    public float getWidth(){
        return widget.getWidth();
    }

    public float getHeight(){
        return widget.getHeight();
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    private void setSprite(){
        bar.setPosition(point.getX(), point.getY());
        bar.setSize(widget.getWidth(), widget.getHeight());
    }

    public void draw(SpriteBatch batch) {
        setSprite();
        bar.draw(batch);
    }
}
