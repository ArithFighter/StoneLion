package com.arithfighter.not.entity;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    private final Font font;
    private int cardPlayTimes;
    private int score;
    private int energy;
    private final Point cursorPoint = new Point();
    private int token;
    private final int fontSize = 16;

    public GameDataDisplacer(){
        font = new Font(fontSize);
        font.setColor(Color.WHITE);
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setCardPlayTimes(int times){
        cardPlayTimes = times;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void setCursorPoint(int x, int y){
        cursorPoint.set(x,y);
    }

    public void setToken(int token){
        this.token = token;
    }

    public void draw(SpriteBatch batch){
        String posText = "X: " + cursorPoint.getX() + " Y: " + cursorPoint.getY();

        font.draw(batch,posText,0, Gdx.graphics.getHeight());

        font.draw(batch, "Record: "+cardPlayTimes,0,
                Gdx.graphics.getHeight()-fontSize*1.2f);

        font.draw(batch, "Score: "+score,0,
                Gdx.graphics.getHeight()-fontSize*2.4f);

        font.draw(batch, "Energy: "+energy, 0,
                Gdx.graphics.getHeight()-fontSize*3.6f);

        font.draw(batch, "Token: "+token, 0,
                Gdx.graphics.getHeight()-fontSize*4.8f);
    }

    public void dispose(){
        font.dispose();
    }
}
