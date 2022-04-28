package com.arithfighter.not.pojo;

public class ValueHolder {
    private int initValue;
    private int value;

    public ValueHolder(){

    }

    public ValueHolder(int initValue) {
        setInitValue(initValue);
    }

    public void setInitValue(int initValue){
        this.initValue = initValue;
        value = this.initValue;
    }

    public void setValue(int i){
        if (i<=initValue)
            value = Math.max(0,i);
        else
            value = initValue;
    }

    public int getValue() {
        return value;
    }

    public int getInitValue() {
        return initValue;
    }

    public void decreaseValue(int i) {
        value -= i;
        value = Math.max(0, value);
    }

    public void increaseValue(int i) {
        value += i;
        value = Math.min(initValue, value);
    }
}
