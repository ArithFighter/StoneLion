package com.arithfighter.ccg.cardgame;

import java.util.HashSet;
import java.util.LinkedList;

public class RandomNumListGenerator {
    private static final int MAX_QUANTITY = 9;

    public void generateNumbers(LinkedList<Integer> numberList, HashSet<Integer> numberSet) {
        if (numberList.size() < MAX_QUANTITY) {
            addNumbersToList(numberList, numberSet);
        }
    }

    private void addNumbersToList(LinkedList<Integer> numberList, HashSet<Integer> numberSet) {
        NumSetGenerator nsg = new NumSetGenerator();

        nsg.addNumberUntilEqualToQuantity(MAX_QUANTITY, numberSet);

        numberList.addAll(numberSet);
    }
}

class NumSetGenerator{
    public void addNumberUntilEqualToQuantity(int quantity, HashSet<Integer> numberSet) {
        addRandomNumberToSet(quantity, numberSet);

        while (numberSet.size() < quantity)
            addNumberUntilEqualToQuantity(quantity - numberSet.size(),numberSet);
    }

    private void addRandomNumberToSet(int quantity, HashSet<Integer> numberSet) {
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
