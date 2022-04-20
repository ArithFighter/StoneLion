package com.arithfighter.not.pojo;

public class ValueHolder {
    private int initValue;
    private int value;

    public ValueHolder(){

    }

    public ValueHolder(int max) {
        setInitValue(max);
    }

    public void setInitValue(int max){
        initValue = max;
        value = initValue;
    }

    public int getValue() {
        return value;
    }

    public int getInitValue() {
        return initValue;
    }

    public void updateValue() {
        value = Math.max(0, value);
    }

    public void decreaseValue(int i) {
        value -= i;
    }

    public void increaseValue(int i) {
        value += i;
    }
}
