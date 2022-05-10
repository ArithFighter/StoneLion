package com.arithfighter.not.widget.stagecomponent;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.FontWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBox {
    private final Font text;
    private final FontWidget widget;
    private int capacity;

    public SumBox(Texture texture) {
        widget = new SpriteWidget(texture, 8, 36);
        text = new Font(widget.getFontSize());
    }

    public void setPosition(float x, float y) {
        widget.setPosition(x, y);
    }

    public void draw(int number, SpriteBatch batch) {
        widget.draw(batch);

        changeColor();

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch) {
        String sum = String.valueOf(number);

        float textX = widget.getCenterX(sum);
        float textY = widget.getCenterY();

        text.setColor(Color.WHITE);
        text.draw(batch, sum, textX, textY);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    private void changeColor() {
        if (isCapacityWarning())
            widget.getSprite().setColor(Color.RED);
        else
            widget.getSprite().setColor(Color.TEAL);
    }

    public boolean isCapacityWarning() {
        int capacityWarning = 2;
        return capacity <= capacityWarning;
    }

    public void dispose() {
        text.dispose();
    }
}
