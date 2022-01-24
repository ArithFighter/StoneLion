package com.arithfighter.ccg;

public class Layout implements WindowSetting{
    float margin = GRID_X * 2;

    public float getNumberBoxX(int i, float width) {
        float x = margin;

        for (int j = 0;j<3;j++)
            if (i%3 == j) x += (margin + width) * j;

        return x;
    }

    public float getNumberBoxY(int i, float height) {
        float y = CENTER_Y + GRID_Y * 5.4f;

        for(int j =0; j<5; j++)
            if (i/4 == j)
                y -= (margin + height/2) * j;

        return y;
    }
}
