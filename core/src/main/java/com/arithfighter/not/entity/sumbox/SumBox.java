package com.arithfighter.not.entity.sumbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.FontWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBox {
    private Font font;
    private final FontWidget widget;
    private int capacity;

    public SumBox(Texture texture) {
        widget = new SpriteWidget(texture, 8);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setPosition(float x, float y) {
        widget.setPosition(x, y);
    }

    public void draw(int number, SpriteBatch batch) {
        widget.setFontSize(font.getSize());
        widget.draw(batch);

        changeColor();

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch) {
        String sum = String.valueOf(number);

        float textX = widget.getCenterX(sum);
        float textY = widget.getCenterY();

        font.setColor(Color.WHITE);
        font.draw(batch, sum, textX, textY);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void init(){
        capacity = 0;
    }

    private void changeColor() {
        if (isCapacityWarning())
            widget.getSprite().setColor(Color.RED);
        else
            widget.getSprite().setColor(Color.TEAL);
    }

    public boolean isCapacityWarning() {
        int capacityWarning = 4;
        return capacity < capacityWarning;
    }
}
