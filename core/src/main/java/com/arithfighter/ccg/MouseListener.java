package com.arithfighter.ccg;

import com.badlogic.gdx.Gdx;

public class MouseListener {
    public int getMouseX() {
        return Gdx.input.getX();
    }

    public int getMouseY() {
        return Gdx.graphics.getHeight()-Gdx.input.getY();
    }
}
