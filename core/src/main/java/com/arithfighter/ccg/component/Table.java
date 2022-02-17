package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Table {
    int deskX;
    int deskY;
    float deskWidth;
    float deskHeight;
    float scale = 14;
    Sprite desk;

    public Table(Texture texture, int x, int y){
        deskX = x;
        deskY = y;
        deskWidth = texture.getWidth()*scale;
        deskHeight = texture.getHeight()*scale;

        desk = new Sprite(texture);
        desk.setPosition(deskX,deskY);
        desk.setSize(deskWidth, deskHeight);
    }
    
    public void draw(SpriteBatch batch){
        desk.draw(batch);
    }
    
    public boolean isOnTable(float x, float y){
        return x > deskX && x < deskX + deskWidth &&
                y > deskY && y < deskY + deskHeight;
    }
}
