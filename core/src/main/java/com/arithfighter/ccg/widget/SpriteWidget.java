package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteWidget {
    private final Sprite sprite;
    private final Widget widget;
    private final Point point;

    public SpriteWidget(Texture texture, float scale, int fontSize){
        widget = new Widget(fontSize);
        widget.setSize(texture, scale);
        point = widget.getPoint();

        sprite = new Sprite(texture);
    }

    public SpriteWidget(Texture texture, float scale){
        widget = new Widget();
        widget.setSize(texture, scale);
        point = widget.getPoint();

        sprite = new Sprite(texture);
    }

    public Point getPoint(){
        return point;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public int getFontSize(){
        return widget.getFontSize();
    }

    public float getCenterX(String content){
        return widget.getCenterX(content);
    }

    public float getCenterY(){
        return widget.getCenterY();
    }

    private void setSprite(){
        sprite.setSize(widget.getWidth(), widget.getHeight());
        sprite.setPosition(point.getX(), point.getY());
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    public void draw(SpriteBatch batch){
        setSprite();
        sprite.draw(batch);
    }
}
