package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataAccessor {
    private final Font font;
    private int cardPlayTimes;
    private int score;
    private final int fontSize = 16;

    public GameDataAccessor(){
        font = new Font(fontSize);
        font.setColor(Color.WHITE);
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setCardPlayTimes(int times){
        cardPlayTimes = times;
    }

    public void draw(int x, int y, int energy, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        font.draw(batch,posText,0, Gdx.graphics.getHeight());

        font.draw(batch, "Record: "+cardPlayTimes,0,
                Gdx.graphics.getHeight()-fontSize*1.2f);

        font.draw(batch, "Score: "+score,0,
                Gdx.graphics.getHeight()-fontSize*2.2f);

        font.draw(batch, "Energy: "+energy, 0,
                Gdx.graphics.getHeight()-fontSize*3.2f);
    }

    public void dispose(){
        font.dispose();
    }
}
