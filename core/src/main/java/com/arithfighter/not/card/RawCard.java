package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Shape;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RawCard {
    private final Point initPoint;
    private final Point point;
    private Shape shape;
    private final Sprite sprite;

    public RawCard(float initX, float initY, Texture texture){
        initPoint = new Point(initX,initY);
        point = new Point(initX,initY);
        sprite = new Sprite(texture);
    }

    public void addShape(Texture texture, float scale){
        setShape(texture.getWidth(), texture.getHeight(), scale);
    }

    private void setShape(float width, float height, float scale){
        Shape shape = new Shape();
        shape.setWidth(width*scale);
        shape.setHeight(height*scale);
        this.shape = shape;
    }

    public void setSprite(){
        sprite.setPosition(point.getX(), point.getY());
        sprite.setSize(shape.getWidth(), shape.getHeight());
    }

    public Point getInitPoint(){
        return initPoint;
    }

    public Point getPoint(){
        return point;
    }

    public Shape getShape(){
        return shape;
    }

    public Sprite getSprite(){
        return sprite;
    }
}
