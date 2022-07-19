package com.arithfighter.not.entity.sum;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.FontWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumDisplacer {
    private Font font;
    private final FontWidget widget;
    private int capacity;
    private boolean isDisable = false;

    public SumDisplacer(Texture texture, float scale) {
        widget = new SpriteWidget(texture, scale);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setPosition(float x, float y) {
        widget.setPosition(x, y);
    }

    public void setDisable(){
        isDisable = true;
    }

    public void draw(int number, SpriteBatch batch) {
        widget.setFontSize(font.getSize());
        widget.draw(batch);

        if (!isDisable){
            drawText(number, batch);
        }
        changeColor();
    }

    private void drawText(int number, SpriteBatch batch) {
        String sum = String.valueOf(number);

        float textX = widget.getCenterX(sum);
        float textY = widget.getCenterY()-widget.getWidget().getHeight()/4;

        font.setColor(Color.BLACK);
        font.draw(batch, sum, textX, textY);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void init(){
        capacity = 0;
        isDisable = false;
    }

    private void changeColor() {
        if (isCapacityWarning())
            widget.getSprite().setColor(Color.RED);
        else
            widget.getSprite().setColor(Color.WHITE);
    }

    public boolean isCapacityWarning() {
        int capacityWarning = 4;
        return capacity < capacityWarning;
    }
}
