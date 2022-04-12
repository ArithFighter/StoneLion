package com.arithfighter.not.widget;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteWidget implements VisibleWidget, DetectableWidget,
        FontWidget, DetectableFontWidget, FlexibleWidget{
    private final Sprite sprite;
    private final Widget widget;
    private final Point point;
    private int fontSize;

    public SpriteWidget(Texture texture, float scale, int fontSize){
        widget = new Widget();
        widget.setSize(texture, scale);
        point = widget.getPoint();

        this.fontSize = fontSize;

        sprite = new Sprite(texture);
    }

    public SpriteWidget(Texture texture, float scale){
        widget = new Widget();
        widget.setSize(texture, scale);
        point = widget.getPoint();

        sprite = new Sprite(texture);
    }

    public void updateWidth(float width){
        widget.setWidth(width);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Widget getWidget(){
        return widget;
    }

    public float getCenterX(String content) {
        float midLength = widget.getWidth() / 2 - content.length() * fontSize / 2f;

        return point.getX() + midLength;
    }

    public float getCenterY() {
        float midHeight = (widget.getHeight() + fontSize) / 2;

        return point.getY() + midHeight;
    }

    public int getFontSize(){
        return fontSize;
    }

    private void setSprite(){
        sprite.setSize(widget.getWidth(), widget.getHeight());
        sprite.setPosition(point.getX(), point.getY());
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    public void draw(SpriteBatch batch){
        setSprite();
        sprite.draw(batch);
    }

    public boolean isOnWidget(float x, float y){
        return x > point.getX()
                && x < point.getX() + sprite.getWidth()
                && y > point.getY()
                && y < point.getY() + sprite.getHeight();
    }
}
