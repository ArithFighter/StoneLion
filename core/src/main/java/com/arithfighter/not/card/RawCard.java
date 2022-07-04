package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RawCard {
    private final Point initPoint;
    private final Point point;
    private final Rectangle rectangle;
    private final Sprite sprite;

    public RawCard(TextureRegion texture, float scale){
        sprite = new Sprite(texture);
        initPoint = new Point();
        point = new Point();

        this.rectangle = new Rectangle(
                texture.getRegionWidth()*scale,
                texture.getRegionHeight()*scale
        );
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
