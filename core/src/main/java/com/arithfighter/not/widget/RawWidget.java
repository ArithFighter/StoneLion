package com.arithfighter.not.widget;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class RawWidget {
    private Point point;
    private final Rectangle rectangle;

    public RawWidget(){
        rectangle = new Rectangle();
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
        return rectangle.getWidth();
    }

    public float getHeight(){
        return rectangle.getHeight();
    }

    public void setWidth(float width){
        rectangle.setWidth(width);
    }

    public void setHeight(float height) {
        rectangle.setHeight(height);
    }

    private void configSize(float width, float height, float scale){
        rectangle.setWidth(scale*width);
        rectangle.setHeight(scale*height);
    }
}
