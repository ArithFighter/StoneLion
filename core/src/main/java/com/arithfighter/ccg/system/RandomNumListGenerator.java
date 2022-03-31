package com.arithfighter.ccg.system;

import com.arithfighter.ccg.pojo.RandomNumProducer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RandomNumListGenerator {
    private final int maxQuantity;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final HashSet<Integer> numberSet = new HashSet<>();

    public RandomNumListGenerator(int maxQuantity){
        this.maxQuantity = maxQuantity;
    }

    public void clear(){
        numberList.clear();
        numberSet.clear();
    }

    public List<Integer> getNumbers() {
        addNumbersToList();

        return numberList;
    }

    private void addNumbersToList() {
        if (numberList.size() < maxQuantity) {
            addNumberUntilEqualToQuantity(maxQuantity);

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
