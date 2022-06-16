package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBoxService {
    private final NumberBox[] numberBoxes;
    private final static int quantity = 9;

    public NumberBoxService(Texture texture, Font font) {
        numberBoxes = new NumberBox[quantity];

        for (int i = 0; i < quantity; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setFont(font);
        }
    }

    public void setPosition(NumberBoxPlacer placer) {
        for (int i = 0; i < quantity; i++){
            Rectangle rectangle = numberBoxes[i].getRectangle();
            numberBoxes[i].setPosition(
                    placer.getNumberBoxX(i, rectangle.getWidth()),
                    placer.getNumberBoxY(i, rectangle.getHeight())
            );
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public NumberBox[] getNumberBoxes() {
        return numberBoxes;
    }

    public void draw(SpriteBatch batch, int[] numbers) {
        for (int i = 0; i < quantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(batch, numbers[i]);
        }
    }
}
