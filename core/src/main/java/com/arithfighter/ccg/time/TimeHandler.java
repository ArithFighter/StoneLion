package com.arithfighter.ccg.time;

import com.badlogic.gdx.Gdx;

public class TimeHandler {
    private float pastedTime = 0;
    private float currentTime = 0;

    public void updatePastedTime(){
        pastedTime += Gdx.graphics.getDeltaTime();
    }

    public void updateCurrentTime(){
        currentTime+=pastedTime;
    }

    public float getPastedTime(){
        return pastedTime;
    }

    public final float getCurrentTime() {
        return currentTime;
    }

    public final void resetCurrentTime() {
        currentTime-=currentTime;
    }

    public final void resetPastedTime() {
        pastedTime-=pastedTime;
    }
}
