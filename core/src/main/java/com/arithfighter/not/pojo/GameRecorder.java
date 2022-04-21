package com.arithfighter.not.pojo;

public class GameRecorder {
    private final Recorder stagesRecorder;
    private final Recorder winRecorder;
    private final Recorder loseRecorder;
    private final Recorder tokenRecorder;

    public GameRecorder() {
        stagesRecorder = new Recorder();
        winRecorder = new Recorder();
        loseRecorder = new Recorder();
        tokenRecorder = new Recorder();
    }

    public void init() {
        stagesRecorder.reset();
        winRecorder.reset();
        loseRecorder.reset();
        tokenRecorder.reset();
    }

    public Recorder getStagesRecorder() {
        return stagesRecorder;
    }

    public Recorder getWinRecorder() {
        return winRecorder;
    }

    public Recorder getLoseRecorder() {
        return loseRecorder;
    }

    public Recorder getTokenRecorder() {
        return tokenRecorder;
    }
}
