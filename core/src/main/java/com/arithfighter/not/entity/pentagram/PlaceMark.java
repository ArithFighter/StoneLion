package com.arithfighter.not.entity.pentagram;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlaceMark {
    private final DetectableWidget mark;
    private EnchantmentLevel level = EnchantmentLevel.NONE;
    private Point point;
    private boolean isOn = false;

    public PlaceMark(Texture texture, float scale) {
        mark = new SpriteWidget(texture, scale);
    }

    public void setLevel(EnchantmentLevel level) {
        this.level = level;
    }

    public EnchantmentLevel getLevel() {
        return level;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void draw(SpriteBatch batch) {
        mark.getSprite().setColor(level.getColor());
        mark.setPosition(point.getX(), point.getY());
        mark.draw(batch);
    }

    public void on(float x, float y) {
        if (mark.isOnWidget(x, y))
            isOn = true;
    }

    public boolean isOn() {
        return isOn;
    }

    public void off() {
        isOn = false;
    }
}
