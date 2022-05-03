package com.arithfighter.not.pojo;

public class ValueHolder {
    private int maxValue;
    private int minValue;
    private int value;

    public ValueHolder(){
        minValue = 0;
    }

    public ValueHolder(int maxValue) {
        setMaxValue(maxValue);
        minValue = 0;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;
        value = this.maxValue;
    }

    public void setValue(int i){
        if (i<= maxValue)
            value = Math.max(minValue,i);
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
        value = Math.max(minValue, value);
    }

    public void increaseValue(int i) {
        value += i;
        value = Math.min(maxValue, value);
    }
}
