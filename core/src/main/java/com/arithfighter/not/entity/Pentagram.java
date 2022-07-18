package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pentagram {
    private final VisibleWidget pentagram;
    private final PlaceMark mark;
    private Point point;

    public Pentagram(Texture[] textures, float scale){
        pentagram = new SpriteWidget(textures[0], scale);

        mark = new PlaceMark(textures[1], 1.5f);
        mark.setLevel(EnchantmentLevel.LOW);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        pentagram.setPosition(point.getX()-pentagram.getWidget().getWidth()/2, point.getY());
        pentagram.draw(batch);

        mark.setPoint(new Point(point.getX(), point.getY()));
        mark.draw(batch);
    }
}

enum EnchantmentLevel{
    NONE(Color.GRAY, 0,0),
    LOW(Color.SKY,1,3),
    MID(Color.BLUE, 4,6),
    HIGH(Color.PURPLE,7,9);

    private final Color color;
    private final int minBell;
    private final int maxBell;

    EnchantmentLevel(Color color, int minBell, int maxBell) {
        this.color = color;
        this.minBell = minBell;
        this.maxBell = maxBell;
    }

    public Color getColor() {
        return color;
    }

    public int getMinBell() {
        return minBell;
    }

    public int getMaxBell() {
        return maxBell;
    }
}

class PlaceMark{
    private final VisibleWidget mark;
    private EnchantmentLevel level;
    private Point point;

    public PlaceMark(Texture texture, float scale){
        mark = new SpriteWidget(texture, scale);
    }

    public void setLevel(EnchantmentLevel level) {
        this.level = level;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        mark.getSprite().setColor(level.getColor());
        mark.setPosition(point.getX(), point.getY());
        mark.draw(batch);
    }
}