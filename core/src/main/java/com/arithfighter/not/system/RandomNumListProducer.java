package com.arithfighter.not.system;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RandomNumListProducer {
    private int maxQuantity;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final NumberSetGenerator numberSetGenerator;

    public RandomNumListProducer(RandomNumProducer randomNumProducer) {
        numberSetGenerator = new NumberSetGenerator(randomNumProducer);
    }

    public RandomNumListProducer(GameNumProducer gameNumProducer) {
        numberSetGenerator = new NumberSetGenerator(gameNumProducer);
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void clear() {
        numberList.clear();
        numberSetGenerator.getNumberSet().clear();
    }

    public List<Integer> getNumbers() {
        addNumbersToList();
        return numberList;
    }

    private void addNumbersToList() {
        if (numberList.size() < maxQuantity) {
            numberSetGenerator.addNumberUntilReachQuantity(maxQuantity);

            numberList.addAll(numberSetGenerator.getNumberSet());
        }
    }
}

class NumberSetGenerator {
    private final HashSet<Integer> numberSet = new HashSet<>();
    private final RandomNumFactory randomNumFactory;

    public NumberSetGenerator(GameNumProducer gameNumProducer) {
        randomNumFactory = gameNumProducer;
    }

    public NumberSetGenerator(RandomNumProducer randomNumProducer) {
        randomNumFactory = randomNumProducer;
    }

    public HashSet<Integer> getNumberSet() {
        return numberSet;
    }

    public void addNumberUntilReachQuantity(int quantity) {
        addRandomNumberToSet(quantity);

        while (numberSet.size() < quantity)
            addNumberUntilReachQuantity(quantity - numberSet.size());
    }

    private void addRandomNumberToSet(int quantity) {
        for (int i = 0; i < quantity; i++)
            numberSet.add(randomNumFactory.getRandomNum());
    }
}