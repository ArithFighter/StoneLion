package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board{
    private final Sprite table;
    private final SpriteWidget widget;
    private final Point point;

    public Board(Texture texture){
        widget = new SpriteWidget();
        widget.setSize(texture, 14);
        point = widget.point;

        table = new Sprite(texture);
    }

    public void setPosition(int x, int y){
        point.setX(x);
        point.setY(y);
    }

    private void setSprite(){
        table.setPosition(point.getX(), point.getY());
        table.setSize(widget.width, widget.height);
    }

    public void draw(SpriteBatch batch){
        setSprite();
        table.draw(batch);
    }

    public boolean isOnBoard(float x, float y){
        return x > point.getX() && x < point.getX() + widget.width &&
                y > point.getY() && y < point.getY() + widget.height;
    }
}
