package com.arithfighter.not.widget.a1;

import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.RawWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mask {
    private final VisibleWidget widget;

    public Mask(Texture texture, float scale){
        widget = new SpriteWidget(texture, scale);
    }

    public void setPosition(float x, float y){
        widget.setPosition(x,y);
    }

    public Rectangle getRectangle(){
        RawWidget raw = widget.getWidget();
        return new Rectangle(raw.getWidth(), raw.getHeight());
    }

    public void draw(SpriteBatch batch, float alpha){
        widget.getSprite().setColor(1,1,1,alpha);
        widget.draw(batch);
    }

    public void draw(SpriteBatch batch){
        widget.getSprite().setColor(Color.BLACK);
        widget.draw(batch);
    }

    public void debug(SpriteBatch batch){
        widget.getSprite().setColor(Color.GREEN);
        widget.draw(batch);
    }
}
