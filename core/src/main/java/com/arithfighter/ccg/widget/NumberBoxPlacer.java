package com.arithfighter.ccg.widget;

public class NumberBoxPlacer{
    float initX;
    float initY;
    float margin;

    public NumberBoxPlacer(float initX, float initY, float margin){
        this.initX = initX;
        this.initY = initY;
        this.margin = margin;
    }

    public float getNumberBoxX(int i, float width) {
        float x = initX;

        for (int j = 0;j<3;j++)
            if (i%3 == j) x += (margin + width) * j;

        return x;
    }

    public float getNumberBoxY(int i, float height) {
        float y = initY;

        for(int j =0; j<5; j++)
            if (i/3 == j)
                y += (margin + height) * j;

        return y;
    }
}
