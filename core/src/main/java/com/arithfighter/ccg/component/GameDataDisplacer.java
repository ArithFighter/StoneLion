package com.arithfighter.ccg.component;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    Font font;
    int fontSize = 16;

    public GameDataDisplacer(){
        font = new Font(fontSize);
    }

    public void drawMousePos(int x, int y, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        font.setColor(Color.WHITE);
        font.draw(batch,posText,0, WindowSetting.MAX_WINDOW_HEIGHT);

    }

    public void drawRecord(int number, SpriteBatch batch){
        font.setColor(Color.WHITE);
        font.draw(batch, "Record: "+number,0,
                WindowSetting.MAX_WINDOW_HEIGHT-fontSize*1.1f);
    }

    public void drawScoreBoard(int score, SpriteBatch batch){
        font.setColor(Color.WHITE);
        font.draw(batch, "Score: "+score,0,
                WindowSetting.MAX_WINDOW_HEIGHT-fontSize*2.1f);
    }

    public void dispose(){
        font.dispose();
    }
}
