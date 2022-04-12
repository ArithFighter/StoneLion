package com.arithfighter.not.pojo;

public class ValueHolder {
    private final int MAX_VALUE;
    private int value;

    public ValueHolder(int max) {
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
