package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    Font mousePos;

    public GameDataDisplacer(){
        mousePos = new Font(16);
    }

    public void draw(int x, int y, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        mousePos.setColor(Color.WHITE);
        mousePos.draw(batch,posText,0, WindowSetting.MAX_WINDOW_HEIGHT);
    }

    public void dispose(){
        mousePos.dispose();
    }
}
