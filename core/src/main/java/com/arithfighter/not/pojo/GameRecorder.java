package com.arithfighter.not.pojo;

public class GameRecorder {
    private final Recorder[] recorders;

    public GameRecorder() {
        recorders = new Recorder[4];
        for (int i =0; i< recorders.length;i++)
            recorders[i] = new Recorder();
    }

    public void init() {
        for (Recorder r:recorders)
            r.reset();
    }

    public Recorder getStagesRecorder() {
        return recorders[0];
    }

    public Recorder getWinRecorder() {
        return recorders[1];
    }

    public Recorder getLoseRecorder() {
        return recorders[2];
    }

    public Recorder getTokenRecorder() {
        return recorders[3];
    }
}
