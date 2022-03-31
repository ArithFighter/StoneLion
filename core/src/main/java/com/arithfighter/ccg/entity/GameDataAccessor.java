package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.pojo.Recorder;
import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataAccessor {
    private final Font font;
    private final Recorder playRecorder;
    private final Recorder scoreRecorder;
    private final int fontSize = 16;

    public GameDataAccessor(){
        font = new Font(fontSize);
        playRecorder = new Recorder();
        scoreRecorder = new Recorder();
        font.setColor(Color.WHITE);
    }

    public void updateScore(int change){
        scoreRecorder.update(change);
    }

    public void updatePlayTimes(){
        playRecorder.update(1);
    }

    public void resetRecorder(){
        scoreRecorder.reset();
        playRecorder.reset();
    }

    public void draw(int x, int y, int energy,SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        font.draw(batch,posText,0, Gdx.graphics.getHeight());

        font.draw(batch, "Record: "+playRecorder.getRecord(),0,
                Gdx.graphics.getHeight()-fontSize*1.2f);

        font.draw(batch, "Score: "+scoreRecorder.getRecord(),0,
                Gdx.graphics.getHeight()-fontSize*2.2f);

        font.draw(batch, "Energy: "+energy, 0,
                Gdx.graphics.getHeight()-fontSize*3.2f);
    }

    public void dispose(){
        font.dispose();
    }
}
