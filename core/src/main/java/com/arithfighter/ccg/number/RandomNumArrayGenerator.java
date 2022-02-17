package com.arithfighter.ccg.number;

import java.util.HashSet;
import java.util.LinkedList;

public class RandomNumArrayGenerator {
    private static final int MAX_QUANTITY = 9;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final HashSet<Integer> numberSet = new HashSet<>();

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
