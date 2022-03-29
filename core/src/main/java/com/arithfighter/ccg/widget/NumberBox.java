package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox{
    private final Font text;
    private final Sprite box;
    private final SpriteWidget widget;
    private final Point point;

    public NumberBox(Texture texture) {
        widget = new SpriteWidget(32);
        widget.setSize(texture, 3.5f);
        point = widget.getPoint();

        text = new Font(widget.getFontSize());

        box = new Sprite(texture);
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    public float getWidth() {
        return widget.getWidth();
    }

    public float getHeight() {
        return widget.getHeight();
    }

    public void draw(SpriteBatch batch, int number) {
        setSprite();

        box.draw(batch);

        addText(batch, number);
    }

    private void setSprite() {
        box.setSize(widget.getWidth(), widget.getHeight());
        box.setPosition(point.getX(), point.getY());
        box.setColor(0, 0.9f, 0.9f, 1);
    }

    private void addText(SpriteBatch batch, int number) {
        String content = String.valueOf(number);

        float textX = widget.getCenterX(content);
        float textY = widget.getCenterY();

        changeNumColor(number);

        text.draw(batch, content, textX, textY);
    }

    private void changeNumColor(int number) {
        int purpleNum = 15;
        int blueNum = 21;
        int yellowNum = 99;

        if (number < purpleNum) text.setColor(Color.PURPLE);

        else if (number <= blueNum) text.setColor(Color.BLUE);

        else if (number < yellowNum) text.setColor(Color.YELLOW);
    }

    public void dispose() {
        text.dispose();
    }
}
