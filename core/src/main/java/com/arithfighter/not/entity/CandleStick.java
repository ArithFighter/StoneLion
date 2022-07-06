package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CandleStick {
    private final VisibleWidget candle;
    private final VisibleWidget fire;
    private final VisibleWidget handStick;
    private Point point;
    private float scale = 7;

    public CandleStick(Texture[] textures){
        candle = new SpriteWidget(textures[0], scale);

        fire = new SpriteWidget(textures[1], scale);

        handStick = new SpriteWidget(textures[2], scale);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        Sprite stickSprite = handStick.getSprite();
        Rectangle stickR = new Rectangle(stickSprite.getWidth(),stickSprite.getHeight());

        Sprite candleSprite = candle.getSprite();
        Rectangle candleR = new Rectangle(candleSprite.getWidth(), candleSprite.getHeight());

        Sprite fireSprite = fire.getSprite();
        Rectangle fireR = new Rectangle(fireSprite.getWidth(), fireSprite.getHeight());

        handStick.setPosition(point.getX(), point.getY());
        candle.setPosition(point.getX() + (stickR.getWidth()- candleR.getWidth())/2, point.getY()+ stickR.getHeight()-scale*5);
        fire.setPosition(point.getX()+ (stickR.getWidth()- fireR.getWidth())/2, point.getY()+ stickR.getHeight()+ candleR.getHeight()-scale*5);

        handStick.draw(batch);
        candle.draw(batch);
        fire.draw(batch);
    }
}

class Candle{
    private final VisibleWidget candle;
    private final VisibleWidget fire;
    private float scale = 7;
    private Point point;

    public Candle(Texture[] textures){
        candle = new SpriteWidget(textures[0], scale);

        fire = new SpriteWidget(textures[1], scale);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch) {
        Sprite candleSprite = candle.getSprite();
        Rectangle candleR = new Rectangle(candleSprite.getWidth(), candleSprite.getHeight());

        Sprite fireSprite = fire.getSprite();
        Rectangle fireR = new Rectangle(fireSprite.getWidth(), fireSprite.getHeight());
        candle.setPosition(point.getX() - candleR.getWidth()/2, point.getY()-scale*5);
        fire.setPosition(point.getX()- fireR.getWidth()/2, point.getY()+ candleR.getHeight()-scale*5);

        candle.draw(batch);
        fire.draw(batch);
    }
}