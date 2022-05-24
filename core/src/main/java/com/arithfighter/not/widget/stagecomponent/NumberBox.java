package com.arithfighter.not.widget.stagecomponent;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.FontWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox{
    private Font font;
    private final FontWidget widget;

    public NumberBox(Texture texture) {
        widget = new SpriteWidget(texture, 3.5f, 32);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setPosition(float x, float y){
        widget.setPosition(x, y);
    }

    public float getWidth() {
        return widget.getWidget().getWidth();
    }

    public float getHeight() {
        return widget.getWidget().getHeight();
    }

    public void draw(SpriteBatch batch, int number) {
        widget.setFontSize(font.getSize());

        setSprite();

        widget.draw(batch);

        addText(batch, number);
    }

    private void setSprite() {
        widget.getSprite().setColor(0, 0.9f, 0.9f, 1);
    }

    private void addText(SpriteBatch batch, int number) {
        String content = String.valueOf(number);

        float textX = widget.getCenterX(content);
        float textY = widget.getCenterY();

        changeNumColor(number);

        font.draw(batch, content, textX, textY);
    }

    private void changeNumColor(int number) {
        int purpleNum = 15;
        int blueNum = 21;
        int yellowNum = 99;

        if (number < purpleNum) font.setColor(Color.PURPLE);

        else if (number <= blueNum) font.setColor(Color.BLUE);

        else if (number < yellowNum) font.setColor(Color.YELLOW);
    }

    public void dispose() {
    }
}
