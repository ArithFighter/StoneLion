package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox extends RawWidget {
    Font text;
    Sprite box;

    public NumberBox(Texture texture, float initX, float initY) {
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 3.5f);

        configFont(32);

        text = new Font(fontSize);

        box = new Sprite(texture);
        box.setPosition(widgetX, widgetY);
        box.setSize(widgetWidth, widgetHeight);
    }

    public float getWidth() {
        return widgetWidth;
    }

    public float getHeight() {
        return widgetHeight;
    }

    public void draw(int number, SpriteBatch batch) {
        addBoxSprite(batch);

        addText(number, batch);
    }

    private void addBoxSprite(SpriteBatch batch) {
        box.setColor(0, 0.9f, 0.9f, 1);
        box.draw(batch);
    }

    private void addText(int number, SpriteBatch batch) {
        String content = String.valueOf(number);
        float textX = widgetX + widgetWidth / 2 - content.length() * fontSize / 2f;
        float textY = widgetY + (widgetHeight + fontSize) / 2;

        changeNumColor(number);

        text.draw(batch, content, textX, textY);
    }

    private void changeNumColor(int number) {
        int purpleNum = 15;
        int blueNum = 21;
        int yellowNum = 99;

        if (number < purpleNum)
            text.setColor(Color.PURPLE);

        else if (number <= blueNum)
            text.setColor(Color.BLUE);

        else if (number < yellowNum)
            text.setColor(Color.YELLOW);
    }

    public void dispose() {
        text.dispose();
    }
}
