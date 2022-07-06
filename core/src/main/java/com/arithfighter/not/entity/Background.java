package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private final VisibleWidget forest;

    public Background(Texture texture){
        forest = new SpriteWidget(texture, 8f);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(2,3);

        forest.setPosition(
                layoutSetter.getGrid().getWidth()-forest.getWidget().getWidth()/2,
                layoutSetter.getGrid().getHeight()
        );
    }

    public void draw(SpriteBatch batch){
        forest.draw(batch);
    }
}
