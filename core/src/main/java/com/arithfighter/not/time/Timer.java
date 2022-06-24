package com.arithfighter.not.time;

public class Timer {
    private boolean isTimesOut;
    private final TimeHandler timeHandler;
    private float time = 0;

    public Timer() {
        isTimesOut = false;
        timeHandler = new TimeHandler();
    }

    public void init() {
        isTimesOut = false;
        timeHandler.resetPastedTime();
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isTimesOut() {
        return isTimesOut;
    }

    public void update() {
        timeHandler.updatePastedTime();

        if (timeHandler.getPastedTime() > time)
            isTimesOut = true;
    }
}
