package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.arithfighter.ccg.widget.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoard {
    Font score;
    int fontSize = 18;
    BitmapFont bitmapFont;

    public void create(){
        score = new Font();
        score.create(fontSize);
        bitmapFont = score.getBitmapFont();
    }

    public void draw(String content, SpriteBatch batch){
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.draw(batch,content,
                WindowSetting.MAX_WINDOW_WIDTH-content.length()*fontSize,
                WindowSetting.MAX_WINDOW_HEIGHT);
    }

    public void dispose(){
        score.dispose();
    }
}
