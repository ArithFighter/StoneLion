package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BambooForest {
    private final VisibleWidget bamboos;

    public BambooForest(Texture texture){
        bamboos = new SpriteWidget(texture, 10);
    }

    public void setPoint(Point point){
        bamboos.setPosition(point.getX(), point.getY());
    }

    public void draw(SpriteBatch batch){
        bamboos.draw(batch);
    }
}
