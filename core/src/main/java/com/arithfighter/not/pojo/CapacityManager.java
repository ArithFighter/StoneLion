package com.arithfighter.not.pojo;

public class CapacityManager {
    private final int initCapacity;
    private int capacity;

    public CapacityManager(int i){
        initCapacity = i;
        capacity= initCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void update() {
        capacity--;
    }

    public void initialize() {
        capacity = initCapacity;
    }

    public boolean isEmpty() {
        return capacity == initCapacity;
    }

    public boolean isFull() {
        return capacity == 0;
    }
}
