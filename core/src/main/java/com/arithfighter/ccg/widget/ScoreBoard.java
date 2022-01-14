package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.WindowSetting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoard {
    Font score;
    int fontSize = 18;

    public ScoreBoard(){
        score = new Font(fontSize);
    }

    public void draw(String content, SpriteBatch batch){
        score.setColor(Color.WHITE);
        score.draw(batch,content,
                WindowSetting.MAX_WINDOW_WIDTH-content.length()*fontSize,
                WindowSetting.MAX_WINDOW_HEIGHT);
    }

    public void dispose(){
        score.dispose();
    }
}
