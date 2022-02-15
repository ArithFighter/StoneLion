package com.arithfighter.ccg.component;

import com.arithfighter.ccg.accessor.Recorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    Font font;
    Recorder playRecorder;
    Recorder scoreRecorder;
    int fontSize = 16;

    public GameDataDisplacer(){
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

    public void draw(int x, int y, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        font.draw(batch,posText,0, Gdx.graphics.getHeight());

        font.draw(batch, "Record: "+playRecorder.getRecord(),0,
                Gdx.graphics.getHeight()-fontSize*1.1f);

        font.draw(batch, "Score: "+scoreRecorder.getRecord(),0,
                Gdx.graphics.getHeight()-fontSize*2.1f);
    }

    public void dispose(){
        font.dispose();
    }
}
