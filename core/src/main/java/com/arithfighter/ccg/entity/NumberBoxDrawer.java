package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.GRID_X;
import static com.arithfighter.ccg.WindowSetting.GRID_Y;

public class NumberBoxDrawer{
    private final NumberBox[] numberBoxes;
    private final static int maxQuantity = 9;

    public NumberBoxDrawer(Texture texture){
        numberBoxes = new NumberBox[maxQuantity];

        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer();

        for (int i = 0; i < maxQuantity; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public int getMaxQuantity(){
        return maxQuantity;
    }

    public NumberBox[] getNumberBoxes(){
        return numberBoxes;
    }

    public void draw(SpriteBatch batch, int[] numbers){
        for (int i = 0; i < maxQuantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(batch, numbers[i]);
        }
    }

    public void dispose(){
        for (NumberBox numberBox : numberBoxes)
            numberBox.dispose();
    }
}

class NumberBoxPlacer {
    private final float initX = GRID_X * 9.5f;
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
