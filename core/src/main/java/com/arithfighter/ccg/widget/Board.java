package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board extends Widget {
    Sprite table;

    public Board(Texture texture, int x, int y){
        configWidget(x,y,texture.getWidth(), texture.getHeight(), 14);

        table = new Sprite(texture);
        table.setPosition(this.posX, this.posY);
        table.setSize(width, height);
    }
    
    public void draw(SpriteBatch batch){
        table.draw(batch);
    }
    
    public boolean isOnBoard(float x, float y){
        return x > this.posX && x < this.posX + width &&
                y > this.posY && y < this.posY + height;
    }
}
