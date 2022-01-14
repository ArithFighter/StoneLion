package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.arithfighter.ccg.widget.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    Font mousePos;
    BitmapFont bitmapFont;

    public void create(){
        mousePos = new Font();
        mousePos.create(16);
        bitmapFont = mousePos.getBitmapFont();
    }

    public void draw(int x, int y, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        bitmapFont.setColor(Color.WHITE);
        bitmapFont.draw(batch,posText,0, WindowSetting.MAX_WINDOW_HEIGHT);
    }

    public void dispose(){
        mousePos.dispose();
    }
}
