package com.arithfighter.not.pojo;

public class Recorder {
    private int initValue = 0;
    private int record;

    public Recorder(){

    }

    public Recorder(int initValue){
        this.initValue = initValue;
        record = initValue;
    }

    public int getRecord(){
        return record;
    }

    public void update(int change){
        record+=change;
    }

    public void reset(){
        record=initValue;
    }
}
