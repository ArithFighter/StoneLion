package com.arithfighter.not.time;

import com.badlogic.gdx.Gdx;

public class TimeHandler {
    private float pastedTime = 0;

    public void updatePastedTime(){
        pastedTime += Gdx.graphics.getDeltaTime();
    }

    public float getPastedTime(){
        return pastedTime;
    }

    public final void resetPastedTime() {
        pastedTime-=pastedTime;
    }
}
