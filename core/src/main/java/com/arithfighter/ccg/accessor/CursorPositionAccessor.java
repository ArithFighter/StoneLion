package com.arithfighter.ccg.accessor;

import com.badlogic.gdx.Gdx;

public class CursorPositionAccessor {
    private int mouseX, mouseY;

    public int getX(){
        return mouseX;
    }

    public int getY(){
        return mouseY;
    }

    public void update(){
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
    }
}
