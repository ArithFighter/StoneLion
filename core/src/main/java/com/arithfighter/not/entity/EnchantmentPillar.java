package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnchantmentPillar {
    private final VisibleWidget pillarLeft;
    private final VisibleWidget pillarRight;
    private final Ropes ropes;

    public EnchantmentPillar(Texture[] textures){
        float scale = 8f;
        pillarLeft = new SpriteWidget(textures[0], scale);

        pillarRight = new SpriteWidget(textures[1], scale);

        ropes = new Ropes(textures[2], scale);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(11,6);

        Rectangle grid = layoutSetter.getGrid();

        pillarLeft.setPosition(grid.getWidth()*1, 0);
        pillarRight.setPosition(grid.getWidth()*8, 0);
        ropes.setPoint(new Point(grid.getWidth()*2, grid.getHeight()*2));
    }

    public void draw(SpriteBatch batch){
        ropes.draw(batch);
        pillarLeft.draw(batch);
        pillarRight.draw(batch);
    }
}

class Ropes{
    private final VisibleWidget[] ropes;

    public Ropes(Texture texture, float scale) {
        ropes = new VisibleWidget[3];

        for (int i = 0;i< ropes.length;i++){
            ropes[i] = new SpriteWidget(texture, scale);
        }
    }

    public void setPoint(Point point){
        for (int i = 0;i< ropes.length;i++)
            ropes[i].setPosition(point.getX(), point.getY()+i*200);
    }

    public void draw(SpriteBatch batch){
        for (VisibleWidget v:ropes)
            v.draw(batch);
    }
}