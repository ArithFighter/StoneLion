package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.system.RandomNumListProducer;
import com.arithfighter.not.system.RandomNumProducer;

import java.util.List;

class RandomIndexPicker {
    RandomNumProducer randomNumProducer;
    RandomNumListProducer randomNumListProducer;

    public RandomIndexPicker(int size) {
        randomNumProducer = new RandomNumProducer(size - 1, 0);
        randomNumListProducer = new RandomNumListProducer(randomNumProducer);
    }

    public void setQuantity(int quantity) {
        randomNumListProducer.setMaxQuantity(quantity);
    }

    public List<Integer> getIndexes() {
        return randomNumListProducer.getNumbers();
    }

    public void clear() {
        randomNumListProducer.clear();
    }
}
