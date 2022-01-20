package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    Font mousePos;
    Font recorder;
    int fontSize = 16;

    public GameDataDisplacer(){
        mousePos = new Font(fontSize);
        recorder = new Font(fontSize);
    }

    public void drawMousePos(int x, int y, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        mousePos.setColor(Color.WHITE);
        mousePos.draw(batch,posText,0, WindowSetting.MAX_WINDOW_HEIGHT);

    }

    public void drawRecord(int number, SpriteBatch batch){
        recorder.setColor(Color.WHITE);
        recorder.draw(batch, "Record: "+number,0,
                WindowSetting.MAX_WINDOW_HEIGHT-fontSize*1.1f);
    }

    public void dispose(){
        mousePos.dispose();
        recorder.dispose();
    }
}
