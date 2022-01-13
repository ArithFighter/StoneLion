package com.arithfighter.ccg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Desk {
    int deskX;
    int deskY;
    int deskWidth = WindowSetting.GRID_X*9;
    int deskHeight = WindowSetting.GRID_Y*5;
    
    public Desk(int x, int y){
        deskX = x;
        deskY = y;
    }
    
    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(deskX, deskY, deskWidth, deskHeight);
    }
    
    public boolean isOnDesk(float x, float y){
        return x > deskX &&
                x < deskX + deskWidth &&
                y > deskY &&
                y < deskY + deskHeight;
    }
}
