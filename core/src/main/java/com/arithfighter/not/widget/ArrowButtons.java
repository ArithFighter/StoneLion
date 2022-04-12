package com.arithfighter.not.widget;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArrowButtons {
    private final Button leftArrow;
    private final Button rightArrow;
    private Point point;

    public ArrowButtons(Texture[] textures, float scale) {
        leftArrow = new Button(textures[8], scale);

        rightArrow = new Button(textures[9], scale);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint(){
        return point;
    }

    public void drawLeftArrow(SpriteBatch batch) {
        leftArrow.setPosition(point.getX(), point.getY());
        leftArrow.draw(batch, "");
    }

    public void drawRightArrow(SpriteBatch batch, float width) {
        rightArrow.setPosition(point.getX() + width, point.getY());
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
