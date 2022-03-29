package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PanelHighLight {
    private final Sprite square;
    private final Widget widget;
    private final Point point;

    public PanelHighLight(Texture texture){
        widget = new Widget();
        widget.setSize(texture, 1.8f);
        point = widget.getPoint();

        square = new Sprite(texture);
    }

    private void setupSquare(){
        square.setSize(widget.getWidth(), widget.getHeight());
        square.setPosition(point.getX(), point.getY());
    }

    public void setPosition(int x, int y){
        point.set(x,y);
    }

    public void draw(SpriteBatch batch){
        setupSquare();
        square.draw(batch);
    }
}
