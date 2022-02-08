package com.arithfighter.ccg;

public class AutoResetHandler {
    private final static int initCondition = 6;
    private int condition = initCondition;

    public int getCondition(){
        return condition;
    }

    public void update(){
        condition--;
    }

    public void initialize(){
        condition = initCondition;
    }

    public boolean isTimeToReset(){
        return condition == 0;
    }
}
