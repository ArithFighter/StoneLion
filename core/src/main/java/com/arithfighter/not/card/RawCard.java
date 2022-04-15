package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Shape;
import com.badlogic.gdx.graphics.Texture;

public class RawCard {
    private final Point initPoint;
    private final Point point;
    private Shape shape;

    public RawCard(float initX, float initY){
        initPoint = new Point(initX,initY);
        point = new Point(initX,initY);
    }

    public void setSize(Texture texture, float scale){
        setShape(texture.getWidth(), texture.getHeight(), scale);
    }

    private void setShape(float width, float height, float scale){
        Shape shape = new Shape();
        shape.setWidth(width*scale);
        shape.setHeight(height*scale);
        this.shape = shape;
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
}
