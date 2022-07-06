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

    public CandleStick(Texture[] textures){
        candle = new SpriteWidget(textures[0], 3);

        fire = new SpriteWidget(textures[1], 3);

        handStick = new SpriteWidget(textures[2], 3);
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
        candle.setPosition(point.getX() + (stickR.getWidth()- candleR.getWidth())/2, point.getY()+ stickR.getHeight());
        fire.setPosition(point.getX()+ (candleR.getWidth()- fireR.getWidth())/2, point.getY()+ stickR.getHeight()+ candleR.getHeight());

        handStick.draw(batch);
        candle.draw(batch);
        fire.draw(batch);
    }
}
