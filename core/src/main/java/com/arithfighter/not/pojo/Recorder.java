package com.arithfighter.not.pojo;

public class Recorder {
    private int record = 0;

    public Recorder(){

    }

    public Recorder(int initValue){
        record = initValue;
    }

    public int getRecord(){
        return record;
    }

    public void update(int change){
        record+=change;
    }

    public void reset(){
        record-=record;
    }
}
