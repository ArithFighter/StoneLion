package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Shape;

public class RawCard {
    private final Point initPoint;
    private final Point point;
    private Shape shape;

    public RawCard(float initX, float initY){
        shape = new Shape();
        initPoint = new Point(initX,initY);
        point = new Point(initX,initY);
    }

    public Point getInitPoint() {
        return initPoint;
    }

    public Point getPoint() {
        return point;
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }

    public Shape getShape(){
        return shape;
    }
}
