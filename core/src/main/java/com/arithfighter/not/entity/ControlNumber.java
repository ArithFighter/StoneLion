package com.arithfighter.not.entity;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.ValueHolder;
import com.arithfighter.not.widget.ArrowButtons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlNumber {
    private final Font number;
    private final ArrowButtons arrows;
    private final int fontSize;
    private boolean isButtonLock = false;
    private final ValueHolder valueHolder;
    private int valueChange;

    public ControlNumber(Texture[] textures) {
        valueHolder = new ValueHolder();

        arrows = new ArrowButtons(textures, 0.8f);

        fontSize = 25;
        number = new Font(fontSize);
    }

    public void setValueChange(int i){
        valueChange = i;
    }

    public void setInitValue(int value){
        valueHolder.setMAX_VALUE(value);
    }

    public boolean isButtonActive(){
        return arrows.isLeftActive()||arrows.isRightActive();
    }

    public int getValue() {
        return valueHolder.getValue();
    }

    public void setPosition(int x, int y) {
        Point point = new Point();
        point.set(x,y);

        arrows.setPoint(point);
    }

    public void draw(SpriteBatch batch){
        String content = String.valueOf(valueHolder.getValue());
        int width = fontSize*6;

        number.draw(batch, content, arrows.getPoint().getX()+width/2f, arrows.getPoint().getY()+fontSize);

        arrows.drawLeftArrow(batch);
        arrows.drawRightArrow(batch, width);
    }

    public void update() {
        int i = valueHolder.getValue();
        valueHolder.updateValue();

        if (!isButtonLock) {
            if (arrows.isLeftActive() && i > 0)
                valueHolder.decreaseValue(valueChange);
            if (arrows.isRightActive() && i < valueHolder.getMaxValue())
                valueHolder.increaseValue(valueChange);
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
        number.dispose();
        arrows.dispose();
    }
}
