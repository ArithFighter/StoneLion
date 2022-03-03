package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox{
    private final Font text;
    private final Sprite box;
    private final TextureWidget widget;
    private final WidgetSetting widgetSetting;

    public NumberBox(Texture texture, float initX, float initY) {
        widget = new TextureWidget();
        widget.configWidget(texture, 3.5f);
        widget.posX = initX;
        widget.posY = initY;
        widget.fontSize = 32;

        text = new Font(widget.fontSize);

        box = new Sprite(texture);
        box.setPosition(widget.posX, widget.posY);
        box.setSize(widget.width, widget.height);

        widgetSetting = new WidgetSetting(widget.fontSize);
    }

    public float getWidth() {
        return widget.width;
    }

    public float getHeight() {
        return widget.height;
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

        float textX = widgetSetting.getCenterX(widget.posX, widget.width, content);
        float textY = widgetSetting.getCenterY(widget.posY,widget.height);

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
