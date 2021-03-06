package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBoxService {
    private final NumberBox[] numberBoxes;
    private final float scale = 6.7f;
    private final static int quantity = 9;

    public NumberBoxService(Texture texture, Font font) {
        numberBoxes = new NumberBox[quantity];

        for (int i = 0; i < quantity; i++) {
            numberBoxes[i] = new NumberBox(texture, scale);
            numberBoxes[i].setFont(font);
        }
    }

    public NumberBoxService() {
        numberBoxes = new NumberBox[quantity];
    }

    public float getScale() {
        return scale;
    }

    public void setPosition(NumberBoxPlacer placer) {
        for (int i = 0; i < quantity; i++){
            Rectangle rectangle = numberBoxes[i].getRectangle();

            Point point = new Point(
                    placer.getNumberBoxX(i, rectangle.getWidth()),
                    placer.getNumberBoxY(i, rectangle.getHeight())
            );
            numberBoxes[i].setPoint(point);
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
