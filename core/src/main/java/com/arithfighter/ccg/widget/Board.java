package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board{
    private final Sprite table;
    private final SpriteWidget widget;

    public Board(Texture texture){
        widget = new SpriteWidget();
        widget.configWidget(texture, 14);

        table = new Sprite(texture);
    }

    public void setPosition(int x, int y){
        widget.posX = x;
        widget.posY = y;
    }

    private void setSprite(){
        table.setPosition(widget.posX, widget.posY);
        table.setSize(widget.width, widget.height);
    }
    
    public void draw(SpriteBatch batch){
        setSprite();
        table.draw(batch);
    }
    
    public boolean isOnBoard(float x, float y){
        return x > widget.posX && x < widget.posX + widget.width &&
                y > widget.posY && y < widget.posY + widget.height;
    }
}
