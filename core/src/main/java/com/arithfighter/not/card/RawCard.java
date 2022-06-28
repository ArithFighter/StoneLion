package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RawCard {
    private final Point initPoint;
    private final Point point;
    private Rectangle rectangle;
    private final Sprite sprite;

    public RawCard(Texture texture){
        sprite = new Sprite(texture);
        initPoint = new Point();
        point = new Point();
    }

    public void addShape(Texture texture, float scale){
        setShape(texture.getWidth(), texture.getHeight(), scale);
    }

    private void setShape(float width, float height, float scale){
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width*scale);
        rectangle.setHeight(height*scale);
        this.rectangle = rectangle;
    }

    public void setSprite(){
        sprite.setPosition(point.getX(), point.getY());
        sprite.setSize(rectangle.getWidth(), rectangle.getHeight());
    }

    public Point getInitPoint(){
        return initPoint;
    }

    public Point getPoint(){
        return point;
    }

    public Rectangle getShape(){
        return rectangle;
    }

    public Sprite getSprite(){
        return sprite;
    }
}
