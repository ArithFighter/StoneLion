package com.arithfighter.not.widget;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface VisibleWidget {
    void draw(SpriteBatch batch);
    void setPosition(float x, float y);
    Widget getWidget();
    Sprite getSprite();
}
