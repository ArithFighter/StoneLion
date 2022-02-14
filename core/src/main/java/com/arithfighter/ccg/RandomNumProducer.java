package com.arithfighter.ccg;

public class RandomNumProducer{
    private static final int MAX_NUMBER = 30;
    private static final int MIN_NUMBER = 5;

    public int getMax(){
        return MAX_NUMBER;
    }

    public int getRandomNum(){
        return (int)(Math.random() * (MAX_NUMBER - MIN_NUMBER) + MIN_NUMBER + 1);
    }
}