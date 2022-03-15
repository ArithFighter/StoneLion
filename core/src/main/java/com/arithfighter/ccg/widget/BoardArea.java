package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BoardArea {
    private final Sprite table;
    private final SpriteWidget widget;
    private final Point point;

    public BoardArea(Texture texture){
        widget = new SpriteWidget();
        widget.setSize(texture, 14);
        point = widget.getPoint();

        table = new Sprite(texture);
    }

    public void setPosition(int x, int y){
        point.set(x,y);
    }

    private void setSprite(){
        table.setPosition(point.getX(), point.getY());
        table.setSize(widget.getWidth(), widget.getHeight());
    }

    public void draw(SpriteBatch batch){
        setSprite();
        table.draw(batch);
    }

    public boolean isOnBoard(float x, float y){
        return x > point.getX()
                && x < point.getX() + widget.getWidth()
                && y > point.getY()
                && y < point.getY() + widget.getHeight();
    }
}
