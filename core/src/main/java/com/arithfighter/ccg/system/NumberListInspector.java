package com.arithfighter.ccg.system;

public class NumberListInspector {
    private int sumOfNumInspector = -1;
    private boolean allNumAreZero = false;

    public final void inspectNumberList(int[] numberList) {
        checkEveryNumInListAreZero(numberList);
        resetInspector();
    }

    private void resetInspector() {
        sumOfNumInspector =-1;
    }

    private void checkEveryNumInListAreZero(int[] numberList) {
        for (int number : numberList){
            sumOfNumInspector += number;
        }
        allNumAreZero = sumOfNumInspector == -1;
    }

    public boolean isAllNumberAreZero(){
        return allNumAreZero;
    }
}
