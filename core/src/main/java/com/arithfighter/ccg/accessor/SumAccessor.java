package com.arithfighter.ccg.accessor;

public class SumAccessor {
    private int sum = 0;

    public int getSum(){
        return sum;
    }

    public void updateSum(int number){
        sum+=number;
    }

    public void resetSum(){
        sum-=sum;
    }
}
