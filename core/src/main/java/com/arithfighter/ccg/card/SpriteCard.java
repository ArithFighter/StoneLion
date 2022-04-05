package com.arithfighter.ccg.card;

import com.arithfighter.ccg.pojo.Point;
import com.arithfighter.ccg.pojo.Shape;
import com.badlogic.gdx.graphics.Texture;

public class SpriteCard{
    private final RawCard rawCard;

    public SpriteCard(float initX, float initY){
        rawCard = new RawCard();
        rawCard.setInitPoint(new Point(initX,initY));
        rawCard.setPoint(new Point(initX,initY));
    }

    public void setSize(Texture texture, float scale){
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    private void configSize(float width, float height, float scale){
        Shape shape = new Shape();
        shape.setWidth(width*scale);
        shape.setHeight(height*scale);
        rawCard.setShape(shape);
    }

    public Point getInitPoint(){
        return rawCard.getInitPoint();
    }

    public Point getPoint(){
        return rawCard.getPoint();
    }

    public Shape getShape(){
        return rawCard.getShape();
    }
}
