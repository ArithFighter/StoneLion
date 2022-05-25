package com.arithfighter.not.pojo;

public class ValueHolder {
    private int maxValue;
    private int value;

    public ValueHolder(int maxValue) {
        setMaxValue(maxValue);
    }

    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;
        value = this.maxValue;
    }

    public void setValue(int i){
        if (i<= maxValue)
            value = Math.max(0,i);
        else
            value = maxValue;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void decreaseValue(int i) {
        value -= i;
        value = Math.max(0, value);
    }

    public void increaseValue(int i) {
        value += i;
        value = Math.min(maxValue, value);
    }
}
