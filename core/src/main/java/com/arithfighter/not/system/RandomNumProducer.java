package com.arithfighter.not.system;

public class RandomNumProducer implements RandomNumFactory {
    private final int max;
    private final int min;

    public RandomNumProducer(int max, int min){
        this.max = max;
        this.min = min;
    }

    public int getRandomNum(){
        return (int)(Math.random() * (max - min + 1) + min);
    }
}
