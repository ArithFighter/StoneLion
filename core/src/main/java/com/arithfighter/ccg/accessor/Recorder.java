package com.arithfighter.ccg.accessor;

public class Recorder {
    private int record = 0;

    public int getRecord(){
        return record;
    }

    public void update(){
        record++;
    }

    public void reset(){
        record-=record;
    }
}
