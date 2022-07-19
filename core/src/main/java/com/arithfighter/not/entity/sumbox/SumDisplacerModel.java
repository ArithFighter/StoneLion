package com.arithfighter.not.entity.sumbox;

import com.arithfighter.not.pojo.CapacityManager;
import com.arithfighter.not.pojo.Recorder;

public class SumDisplacerModel {
    private final CapacityManager capacityManager;
    private final Recorder sumAccessor;

    public SumDisplacerModel(){
        capacityManager = new CapacityManager(7);
        sumAccessor = new Recorder();
    }

    public void init(){
        sumAccessor.reset();
        capacityManager.initialize();
    }

    public boolean isCapacityFull(){
        return capacityManager.isFull();
    }

    public final int getSum() {
        return sumAccessor.getRecord();
    }

    public final int getCardCapacity() {
        return capacityManager.getCapacity();
    }

    public void update(int number){
        sumAccessor.update(number);

        capacityManager.update();
    }
}
