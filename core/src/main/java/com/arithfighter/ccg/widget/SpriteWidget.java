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

    private void setSprite(){
        sprite.setSize(widget.getWidth(), widget.getHeight());
        sprite.setPosition(point.getX(), point.getY());
    }

    public void setPosition(int x, int y){
        point.set(x,y);
    }

    public void draw(SpriteBatch batch){
        setSprite();
        sprite.draw(batch);
    }
}
