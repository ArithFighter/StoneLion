package com.arithfighter.ccg;

import com.badlogic.gdx.Gdx;

public interface WindowSetting {
    int MAX_WINDOW_HEIGHT = Gdx.graphics.getHeight();
    int MAX_WINDOW_WIDTH = Gdx.graphics.getWidth();

    int CENTER_X = MAX_WINDOW_WIDTH / 2;
    int CENTER_Y = MAX_WINDOW_HEIGHT / 2;
    int GRID_X = CENTER_X / 16;
    int GRID_Y = CENTER_Y / 9;

    float BACKGROUND_RED = 0.08f;
    float BACKGROUND_GREEN = 0.08f;
    float BACKGROUND_BLUE = 0.3f;
    float BACKGROUND_ALPHA = 1f;
}
