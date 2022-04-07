package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.pojo.Point;
import com.arithfighter.ccg.widget.SpriteWidget;
import com.arithfighter.ccg.widget.VisibleWidget;
import com.arithfighter.ccg.widget.Widget;
import com.arithfighter.ccg.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlBar {
    private final VisibleWidget[] grids;
    private final Font font;
    private final ButtonProducer arrows;
    private final int fontSize;
    private boolean isButtonLock = false;
    private final ValueHolder valueHolder;
    private final String content;

    public ControlBar(Texture[] textures, String content) {
        this.content = content;

        valueHolder = new ValueHolder();

        grids = new VisibleWidget[valueHolder.getMaxValue()];

        for (int i = 0; i < grids.length; i++)
            grids[i] = new SpriteWidget(textures[5], 0.5f);

        arrows = new ButtonProducer(textures, 0.8f);

        fontSize = 25;
        font = new Font(fontSize);
    }

    public boolean isButtonActive(){
        return arrows.isLeftActive()||arrows.isRightActive();
    }

    public int getValue() {
        return valueHolder.getValue();
    }

    public void setPosition(int x, int y) {
        for (int i = 0; i < grids.length; i++) {
            grids[i].setPosition(x + grids[i].getWidget().getWidth() * i + 10 * i, y);
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < valueHolder.getValue(); i++)
            grids[i].draw(batch);

        addComponent(batch);
    }

    private void addComponent(SpriteBatch batch) {
        Widget widget = grids[0].getWidget();
        Point point = widget.getPoint();

        font.draw(batch, content, point.getX() - content.length() * fontSize - 50, point.getY() + fontSize / 2f);
        arrows.drawLeftArrow(batch, point.getX() - 50, point.getY());
        arrows.drawRightArrow(batch, point.getX(), point.getY(), (widget.getWidth() + 10) * grids.length);
    }

    public void update() {
        int i = valueHolder.getValue();
        valueHolder.updateValue();

        if (!isButtonLock) {
            if (arrows.isLeftActive() && i >= 0)
                valueHolder.decreaseValue();
            if (arrows.isRightActive() && i < valueHolder.getMaxValue())
                valueHolder.increaseValue();
        }
        isButtonLock = arrows.isLeftActive() || arrows.isRightActive();
    }

    public void activate(float x, float y) {
        arrows.activate(x, y);
    }

    public void deactivate() {
        arrows.deactivate();
    }

    public void dispose() {
        font.dispose();
        arrows.dispose();
    }
}

class ButtonProducer {
    private final Button leftArrow;
    private final Button rightArrow;

    public ButtonProducer(Texture[] textures, float scale) {
        leftArrow = new Button(textures[8], scale);

        rightArrow = new Button(textures[9], scale);
    }

    public void drawLeftArrow(SpriteBatch batch, float x, float y) {
        leftArrow.setPosition(x, y);
        leftArrow.draw(batch, "");
    }

    public void drawRightArrow(SpriteBatch batch, float x, float y, float barWidth) {
        rightArrow.setPosition(x + barWidth, y);
        rightArrow.draw(batch, "");
    }

    public boolean isLeftActive() {
        return leftArrow.isActive();
    }

    public boolean isRightActive() {
        return rightArrow.isActive();
    }

    public void activate(float x, float y) {
        leftArrow.activate(x, y);
        rightArrow.activate(x, y);
    }

    public void deactivate() {
        leftArrow.deactivate();
        rightArrow.deactivate();
    }

    public void dispose() {
        leftArrow.dispose();
        rightArrow.dispose();
    }
}

class ValueHolder {
    private final int MAX_VALUE = 6;
    private int value;

    public ValueHolder() {
        value = MAX_VALUE;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return MAX_VALUE;
    }

    public void updateValue() {
        value = Math.max(0, value);
    }

    public void decreaseValue() {
        value -= 1;
    }

    public void increaseValue() {
        value += 1;
    }
}
