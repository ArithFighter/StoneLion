package com.arithfighter.not.widget;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Shape;
import com.badlogic.gdx.graphics.Texture;

public class Widget {
    private Point point;
    private final Shape shape;

    public Widget(){
        shape = new Shape();
        setPoint(new Point(0,0));
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setSize(Texture texture, float scale) {
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    public float getWidth(){
        return shape.getWidth();
    }

    public float getHeight(){
        return shape.getHeight();
    }

    public void setWidth(float width){
        shape.setWidth(width);
    }

    private void configSize(float width, float height, float scale){
        shape.setWidth(scale*width);
        shape.setHeight(scale*height);
    }
}
