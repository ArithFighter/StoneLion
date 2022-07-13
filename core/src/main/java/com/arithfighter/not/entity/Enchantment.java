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
    private final VisibleWidget rope;

    public Enchantment(Texture[] textures){
        float scale = 8f;
        pillarLeft = new SpriteWidget(textures[0], scale);

        pillarRight = new SpriteWidget(textures[1], scale);

        rope = new SpriteWidget(textures[2], scale);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(11,2);

        Rectangle grid = layoutSetter.getGrid();

        pillarLeft.setPosition(grid.getWidth()*1, 0);
        pillarRight.setPosition(grid.getWidth()*8, 0);
        rope.setPosition(grid.getWidth()*2, 0);
    }

    public void draw(SpriteBatch batch){
        rope.draw(batch);
        pillarLeft.draw(batch);
        pillarRight.draw(batch);
    }
}
