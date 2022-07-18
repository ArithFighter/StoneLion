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

class PlaceMark{
    private final VisibleWidget mark;
    private Point point;

    public PlaceMark(Texture texture, float scale){
        mark = new SpriteWidget(texture, scale);
        mark.getSprite().setColor(Color.BLUE);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        mark.setPosition(point.getX(), point.getY());
        mark.draw(batch);
    }
}