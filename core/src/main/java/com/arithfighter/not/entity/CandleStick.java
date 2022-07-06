package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.FlexibleWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CandleStick {
    private final Candle candle;
    private final VisibleWidget handStick;
    private Point point;

    public CandleStick(Texture[] textures){
        candle = new Candle(textures, 6);

        handStick = new SpriteWidget(textures[2], 7);
    }

    public void setCandleHeight(float height){
        candle.setCandleHeight(height);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        Sprite stickSprite = handStick.getSprite();
        Rectangle stickR = new Rectangle(stickSprite.getWidth(),stickSprite.getHeight());

        handStick.setPosition(point.getX(), point.getY());
        candle.setPoint(new Point(point.getX()+ stickR.getWidth()/2, point.getY()+ stickR.getHeight()));

        handStick.draw(batch);
        candle.draw(batch);
    }
}

class Candle{
    private final FlexibleWidget candle;
    private final VisibleWidget fire;
    private final float scale;
    private Point point;

    public Candle(Texture[] textures, float scale){
        this.scale = scale;
        candle = new SpriteWidget(textures[0], scale);

        fire = new SpriteWidget(textures[1], scale);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setCandleHeight(float height){
        candle.updateHeight(height);
    }

    public void draw(SpriteBatch batch) {
        Sprite candleSprite = candle.getSprite();
        Rectangle candleR = new Rectangle(candleSprite.getWidth(), candleSprite.getHeight());
        Sprite fireSprite = fire.getSprite();
        Rectangle fireR = new Rectangle(fireSprite.getWidth(), fireSprite.getHeight());

        float fix = 10;
        candle.setPosition(point.getX() - candleR.getWidth()/2+fix, point.getY()-scale*7);
        fire.setPosition(point.getX()- fireR.getWidth()/2+fix, point.getY()+ candleR.getHeight()-scale*7);

        candle.draw(batch);
        fire.draw(batch);
    }
}