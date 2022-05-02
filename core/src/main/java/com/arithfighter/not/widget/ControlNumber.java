package com.arithfighter.not.widget;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.ValueHolder;
import com.arithfighter.not.widget.button.ArrowButtons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlNumber {
    private final Font number;
    private final ArrowButtons arrows;
    private final int fontSize;
    private boolean isButtonLock = false;
    private ValueHolder valueHolder;
    private int valueChange;

    public ControlNumber(Texture[] textures) {
        arrows = new ArrowButtons(textures, 0.8f);

        fontSize = 25;
        number = new Font(fontSize);
    }

    public void setValueChange(int i){
        valueChange = i;
    }

    public boolean isButtonActive(){
        return arrows.isLeftActive()||arrows.isRightActive();
    }

    public void setValueHolder(ValueHolder valueHolder){
        this.valueHolder = valueHolder;
    }

    public void setPosition(int x, int y) {
        Point point = new Point();
        point.set(x,y);

        arrows.setPoint(point);
    }

    public void draw(SpriteBatch batch){
        String content = String.valueOf(valueHolder.getValue());
        int width = fontSize*9;

        number.draw(batch, content, arrows.getPoint().getX()+(width-content.length()*fontSize)/2f, arrows.getPoint().getY()+fontSize);

        arrows.drawLeftArrow(batch);
        arrows.drawRightArrow(batch, width);
    }

    public void update() {
        int i = valueHolder.getValue();

        if (!isButtonLock) {
            if (arrows.isLeftActive() && i > 0)
                valueHolder.decreaseValue(valueChange);
            if (arrows.isRightActive() && i < valueHolder.getInitValue())
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
