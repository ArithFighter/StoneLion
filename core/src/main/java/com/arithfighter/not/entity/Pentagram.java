package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pentagram {
    private final VisibleWidget pentagram;
    private Point point;

    public Pentagram(Texture texture, float scale){
        pentagram = new SpriteWidget(texture, scale);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        pentagram.setPosition(point.getX()-pentagram.getWidget().getWidth()/2, point.getY());
        pentagram.draw(batch);
    }
}
