package com.arithfighter.not.entity;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    private final Font font;
    private final Point cursorPoint = new Point();

    public GameDataDisplacer(Font font){
        font.setColor(Color.WHITE);
        this.font = font;
    }

    public void setCursorPoint(int x, int y){
        cursorPoint.set(x,y);
    }

    public void draw(SpriteBatch batch){
        String posText = "X: " + cursorPoint.getX() + " Y: " + cursorPoint.getY();

        font.draw(batch,posText,0, Gdx.graphics.getHeight());
    }
}
