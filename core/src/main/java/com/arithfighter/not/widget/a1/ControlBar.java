package com.arithfighter.not.widget.a1;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.ValueHolder;
import com.arithfighter.not.widget.RawWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.button.ArrowButtons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlBar {
    private final VisibleWidget[] grids;
    private Font font;
    private final ArrowButtons arrows;
    private final ValueHolder valueHolder;
    private boolean isButtonLock = false;

    public ControlBar(Texture[] textures, int max) {
        valueHolder = new ValueHolder(max);

        grids = new VisibleWidget[valueHolder.getMaxValue()];

        for (int i = 0; i < grids.length; i++)
            grids[i] = new SpriteWidget(textures[0], 0.5f);

        arrows = new ArrowButtons(textures[1], 0.8f);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setValue(int value){
        valueHolder.setValue(value);
    }

    public boolean isButtonActive(){
        return arrows.isLeftActive()||arrows.isRightActive();
    }

    public int getValue() {
        return valueHolder.getValue();
    }

    public void setPosition(float x, float y) {
        for (int i = 0; i < grids.length; i++) {
            grids[i].setPosition(x + grids[i].getWidget().getWidth() * i + 10 * i, y);
        }
    }

    public void draw(SpriteBatch batch, String content) {
        for (int i = 0; i < valueHolder.getValue(); i++)
            grids[i].draw(batch);

        addComponent(batch, content);
    }

    private void addComponent(SpriteBatch batch, String content) {
        RawWidget widget = grids[0].getWidget();
        Point point = widget.getPoint();

        int fontSize = font.getSize();
        font.setColor(Color.WHITE);
        font.draw(batch, content, point.getX() - content.length() * fontSize - 50, point.getY() + fontSize / 2f);

        Point arrowPoint = new Point(point.getX()-50, point.getY());
        arrows.setPoint(arrowPoint);

        arrows.drawLeftArrow(batch);
        arrows.drawRightArrow(batch, (widget.getWidth() + 20) * grids.length);
    }

    public void update() {
        int i = valueHolder.getValue();

        if (!isButtonLock) {
            if (arrows.isLeftActive() && i >= 0)
                valueHolder.decreaseValue(1);
            if (arrows.isRightActive() && i < valueHolder.getMaxValue())
                valueHolder.increaseValue(1);
        }
        isButtonLock = arrows.isLeftActive() || arrows.isRightActive();
    }

    public void activate(float x, float y) {
        arrows.on(x, y);
    }

    public void deactivate() {
        arrows.off();
    }
}
