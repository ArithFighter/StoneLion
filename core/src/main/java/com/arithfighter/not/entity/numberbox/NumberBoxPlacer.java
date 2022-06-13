package com.arithfighter.not.entity.numberbox;

import static com.arithfighter.not.WindowSetting.GRID_X;
import static com.arithfighter.not.WindowSetting.GRID_Y;

public class NumberBoxPlacer {
    private final float initX = GRID_X * 7.5f;
    private final float initY = GRID_Y * 5;
    private final float margin = GRID_X;

    public float getNumberBoxX(int i, float width) {
        float x = initX;

        for (int j = 0; j < 3; j++)
            if (i % 3 == j) x += (margin + width) * j;

        return x;
    }

    public float getNumberBoxY(int i, float height) {
        float y = initY;

        for (int j = 0; j < 5; j++)
            if (i / 3 == j)
                y += (margin + height) * j;

        return y;
    }
}
