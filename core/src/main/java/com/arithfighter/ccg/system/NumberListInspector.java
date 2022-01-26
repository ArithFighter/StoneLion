package com.arithfighter.ccg.system;

public class NumberListInspector {
    int sumOfNumInspector = -1;
    boolean allNumAreZero = false;

    public final void inspectNumberList(int[] numberList) {
        checkEveryNumInListAreZero(numberList);
        resetInspector();
    }

    private void resetInspector() {
        sumOfNumInspector -= sumOfNumInspector + 1;
    }

    private void checkEveryNumInListAreZero(int[] numberList) {
        for (int number : numberList)
            sumOfNumInspector += number;

        allNumAreZero = sumOfNumInspector == -1;
    }

    public boolean isAllNumberAreZero(){
        return allNumAreZero;
    }
}
