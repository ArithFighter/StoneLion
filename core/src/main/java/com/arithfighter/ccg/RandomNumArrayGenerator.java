package com.arithfighter.ccg;

import java.util.HashSet;
import java.util.LinkedList;

public class RandomNumArrayGenerator {
    private static final int MAX_QUANTITY = 9;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final HashSet<Integer> numberSet = new HashSet<>();

    public int getMaxQuantity(){
        return MAX_QUANTITY;
    }

    public void clear(){
        numberList.clear();
        numberSet.clear();
    }

    public void setNumberInListToZero(int index){
        numberList.set(index,0);
    }

    public int[] getNumbers() {
        int[] numArray = new int[MAX_QUANTITY];

        addNumbersToList();

        for (int i = 0; i<numberList.size();i++)
            numArray[i] = numberList.get(i);

        return numArray;
    }

    private void addNumbersToList() {
        if (numberList.size() < MAX_QUANTITY) {
            addNumberUntilEqualToQuantity(MAX_QUANTITY);

            numberList.addAll(numberSet);
        }
    }

    private void addNumberUntilEqualToQuantity(int quantity) {
        addRandomNumberToSet(quantity);

        while (numberSet.size() < quantity)
            addNumberUntilEqualToQuantity(quantity - numberSet.size());
    }

    private void addRandomNumberToSet(int quantity) {
        RandomNumProducer rnp = new RandomNumProducer();

        for (int i = 0; i < quantity; i++)
            numberSet.add(rnp.getRandomNum());
    }
}

class RandomNumProducer{
    private static final double MAX_NUMBER = 30;
    private static final double MIN_NUMBER = 5;

    public int getRandomNum(){
        return (int)(Math.random() * (MAX_NUMBER - MIN_NUMBER) + MIN_NUMBER + 1);
    }
}
