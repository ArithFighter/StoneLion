package com.arithfighter.not.system;

import com.arithfighter.not.system.RandomNumFactory;

public class GameNumProducer implements RandomNumFactory {
    private static final int MAX_NUMBER = 30;
    private static final int MIN_NUMBER = 5;

    public int getMaxNumber(){
        return MAX_NUMBER;
    }

    public int getMinNumber(){
        return MIN_NUMBER;
    }

    public int getRandomNum(){
        return (int)(Math.random() * (MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER);
    }
}