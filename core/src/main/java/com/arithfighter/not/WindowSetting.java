package com.arithfighter.not;

import com.badlogic.gdx.Gdx;

public interface WindowSetting {
    int MAX_WINDOW_HEIGHT = Gdx.graphics.getHeight();
    int MAX_WINDOW_WIDTH = Gdx.graphics.getWidth();

    int CENTER_X = MAX_WINDOW_WIDTH / 2;
    int CENTER_Y = MAX_WINDOW_HEIGHT / 2;
    int GRID_X = CENTER_X / 16;
    int GRID_Y = CENTER_Y / 9;
}
