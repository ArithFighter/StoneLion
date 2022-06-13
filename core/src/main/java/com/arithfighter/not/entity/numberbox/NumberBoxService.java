package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBoxService {
    private final NumberBox[] numberBoxes;
    private final static int maxQuantity = 9;

    public NumberBoxService(Texture texture, Font font) {
        numberBoxes = new NumberBox[maxQuantity];

        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer();

        for (int i = 0; i < maxQuantity; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setFont(font);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public NumberBox[] getNumberBoxes() {
        return numberBoxes;
    }

    public void draw(SpriteBatch batch, int[] numbers) {
        for (int i = 0; i < maxQuantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(batch, numbers[i]);
        }
    }
}
