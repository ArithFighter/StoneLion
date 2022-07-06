package com.arithfighter.not.entity;

import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private final VisibleWidget forest;

    public Background(Texture texture){
        forest = new SpriteWidget(texture, 10.5f);
        forest.setPosition(0,0);
    }

    public void draw(SpriteBatch batch){
        forest.draw(batch);
    }
}
