package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.FlexibleWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CandleStick extends DetectCardArea{
    private final Candle candle;
    private final VisibleWidget handStick;
    private final VisibleWidget candleLight;
    private Point point;
    private final  int candleScale = 6;
    private final int stickScale = 7;
    private final Point candlePoint;

    public CandleStick(Texture[] textures){
        candle = new Candle(textures, candleScale);

        handStick = new SpriteWidget(textures[2], stickScale);

        candlePoint = new Point();

        candleLight = new SpriteWidget(textures[5], 8);
        candleLight.getSprite().setColor(1,1,1,0.5f);
    }

    private Point getCandleTopPoint(){
        return new Point(candlePoint.getX(), candlePoint.getY()+candle.getCandleHeight());
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

        candlePoint.set(
                point.getX()+ stickR.getWidth()/2,
                point.getY()+ stickR.getHeight()-candleScale*stickScale
        );
        candle.setPoint(candlePoint);

        handStick.draw(batch);
        candle.draw(batch);

        candleLight.setPosition(
                getCandleTopPoint().getX()-candleLight.getWidget().getWidth()/2,
                getCandleTopPoint().getY()
        );
        candleLight.draw(batch);
    }

    public final void playCardOnCandle(int mouseX, int mouseY) {
        if (isOnCandle(mouseX, mouseY))
            checkCardPlayed();

        initCardPosition();
    }

    public boolean isOnCandle(int mouseX, int mouseY) {
        Rectangle detectArea = new Rectangle(300,300);
        return mouseX > point.getX()
                && mouseX < point.getX() + detectArea.getWidth()
                && mouseY > candlePoint.getY()
                && mouseY < candlePoint.getY()+candle.getCandleHeight() + detectArea.getHeight();
    }
}

class Candle{
    private final FlexibleWidget candle;
    private final VisibleWidget fire;
    private final VisibleWidget head;
    private final VisibleWidget bottom;
    private Point point;

    public Candle(Texture[] textures, float scale){
        candle = new SpriteWidget(textures[0], scale);

        fire = new SpriteWidget(textures[1], scale);

        head = new SpriteWidget(textures[3], scale);

        bottom = new SpriteWidget(textures[4], scale);
    }

    public float getCandleHeight(){
        return candle.getWidget().getHeight();
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setCandleHeight(float height){
        candle.updateHeight(height);
    }

    private void configPosition(){
        Sprite candleS = candle.getSprite();
        float fix = 10;

        candle.setPosition(
                point.getX() - candleS.getWidth()/2+fix,
                point.getY()
        );

        Sprite headS = head.getSprite();
        head.setPosition(
                point.getX() - headS.getWidth()/2+fix,
                point.getY()+ candle.getWidget().getHeight()- headS.getHeight()/2
        );

        Sprite bottomS = bottom.getSprite();
        bottom.setPosition(
                point.getX()- bottomS.getWidth()/2+fix,
                point.getY()
        );

        Sprite fireS = fire.getSprite();
        fire.setPosition(
                point.getX()- fireS.getWidth()/2+fix,
                point.getY()+ candleS.getHeight()+ headS.getHeight()/2
        );
    }

    public void draw(SpriteBatch batch) {
        configPosition();

        candle.draw(batch);
        head.draw(batch);
        bottom.draw(batch);
        fire.draw(batch);
    }
}