package com.arithfighter.not.pojo;

public class ValueHolder {
    private int MAX_VALUE;
    private int value;

    public ValueHolder(){

    }

    public ValueHolder(int max) {
        setMAX_VALUE(max);
    }

    public void setMAX_VALUE(int max){
        MAX_VALUE = max;
        value = MAX_VALUE;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return MAX_VALUE;
    }

    public void updateValue() {
        value = Math.max(0, value);
    }

    public void decreaseValue() {
        value -= 1;
    }

    public void increaseValue() {
        value += 1;
    }
}
