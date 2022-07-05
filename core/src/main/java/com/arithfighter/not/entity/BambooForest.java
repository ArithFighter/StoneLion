package com.arithfighter.not.entity;

import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BambooForest {
    private final VisibleWidget bamboos;

    public BambooForest(Texture[] textures){
        bamboos = new SpriteWidget(textures[0], 10);
    }

    public void draw(SpriteBatch batch){
        bamboos.draw(batch);
    }
}
