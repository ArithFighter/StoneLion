package com.arithfighter.not.widget.button;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArrowButtons {
    private final PanelButton leftArrow;
    private final PanelButton rightArrow;
    private Point point;

    public ArrowButtons(Texture texture, float scale) {
        leftArrow = new PanelButton(texture, scale);

        rightArrow = new PanelButton(texture, scale);
        rightArrow.reverseHorizontal();
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint(){
        return point;
    }

    public void drawLeftArrow(SpriteBatch batch) {
        leftArrow.setPosition(point.getX(), point.getY());
        leftArrow.draw(batch);
    }

    public void drawRightArrow(SpriteBatch batch, float width) {
        rightArrow.setPosition(point.getX() + width, point.getY());
        rightArrow.draw(batch);
    }

    public boolean isLeftActive() {
        return leftArrow.isOn();
    }

    public boolean isRightActive() {
        return rightArrow.isOn();
    }

    public void on(float x, float y) {
        leftArrow.onWhenIsOnButton(x, y);
        rightArrow.onWhenIsOnButton(x, y);
    }

    public void off() {
        leftArrow.off();
        rightArrow.off();
    }
}
