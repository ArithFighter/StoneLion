package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enchantment {
    private final VisibleWidget pillarLeft;
    private final VisibleWidget pillarRight;
    private final VisibleWidget chains;

    public Enchantment(Texture[] textures){
        int scale = 8;
        pillarLeft = new SpriteWidget(textures[0], scale);

        pillarRight = new SpriteWidget(textures[0], scale);

        chains = new SpriteWidget(textures[1], scale);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(7,2);

        Rectangle grid = layoutSetter.getGrid();

        pillarLeft.setPosition(grid.getWidth()*2, 0);
        pillarRight.setPosition(grid.getWidth()*5, 0);
        chains.setPosition(grid.getWidth()*2, 0);
    }

    public void draw(SpriteBatch batch){
        chains.draw(batch);
        pillarLeft.draw(batch);
        pillarRight.draw(batch);
    }
}
