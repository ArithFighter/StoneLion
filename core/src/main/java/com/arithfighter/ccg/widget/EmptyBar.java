package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar{
    private final Sprite bar;
    private final SpriteWidget widget;
    private final Point point;

    public EmptyBar(Texture texture){
        widget = new SpriteWidget(23);
        widget.setSize(texture, 8f);
        point = widget.point;

        bar = new Sprite(texture);
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

    private void setSprite(){
        bar.setPosition(point.getX(), point.getY());
        bar.setSize(widget.width, widget.height);
    }

    public void draw(SpriteBatch batch) {
        setSprite();
        bar.draw(batch);
    }
}
