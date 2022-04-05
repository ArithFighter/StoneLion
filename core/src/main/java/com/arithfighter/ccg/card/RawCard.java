package com.arithfighter.ccg.card;

import com.arithfighter.ccg.pojo.Point;
import com.arithfighter.ccg.pojo.Shape;

public class RawCard {
    private Point initPoint;
    private Point point;
    private Shape shape;

    public void setInitPoint(Point initPoint) {
        this.initPoint = initPoint;
    }

    public void setPoint(Point point) {
        this.point = point;
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
