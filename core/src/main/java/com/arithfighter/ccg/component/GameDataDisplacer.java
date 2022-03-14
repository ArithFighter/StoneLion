package com.arithfighter.ccg.component;

import com.arithfighter.ccg.system.Recorder;
import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDataDisplacer {
    private final Font font;
    private final Recorder playRecorder;
    private final Recorder scoreRecorder;
    private final Recorder energyRecord;
    private final int fontSize = 16;

    public GameDataDisplacer(){
        font = new Font(fontSize);
        playRecorder = new Recorder();
        scoreRecorder = new Recorder();
        energyRecord = new Recorder();
        font.setColor(Color.WHITE);
    }

    public void updateScore(int change){
        scoreRecorder.update(change);
    }

    public void updateEnergy(int change){
        energyRecord.update(change);
    }

    public void consumeEnergy(){energyRecord.reset();}

    public int getEnergy(){
        return energyRecord.getRecord();
    }

    public void updatePlayTimes(){
        playRecorder.update(1);
    }

    public void resetRecorder(){
        scoreRecorder.reset();
        playRecorder.reset();
        energyRecord.reset();
    }

    public void draw(int x, int y, SpriteBatch batch){
        String posText = "X: " + x + " Y: " + y;

        font.draw(batch,posText,0, Gdx.graphics.getHeight());

        font.draw(batch, "Record: "+playRecorder.getRecord(),0,
                Gdx.graphics.getHeight()-fontSize*1.2f);

        font.draw(batch, "Score: "+scoreRecorder.getRecord(),0,
                Gdx.graphics.getHeight()-fontSize*2.2f);

        font.draw(batch, "Energy: "+energyRecord.getRecord(), 0,
                Gdx.graphics.getHeight()-fontSize*3.2f);
    }

    public void dispose(){
        font.dispose();
    }
}
