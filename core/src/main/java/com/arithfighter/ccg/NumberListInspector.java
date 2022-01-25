package com.arithfighter.ccg;

import java.util.LinkedList;

public class NumberListInspector {
    int sumOfNumInspector = -1;
    boolean allNumAreZero = false;

    public final void inspectNumberList(LinkedList<Integer> numberList) {
        checkEveryNumInListAreZero(numberList);
        resetInspector();
    }

    private void resetInspector() {
        sumOfNumInspector -= sumOfNumInspector + 1;
    }

    private void checkEveryNumInListAreZero(LinkedList<Integer> numberList) {
        for (int number : numberList)
            sumOfNumInspector += number;

        allNumAreZero = sumOfNumInspector == -1;
    }

    public boolean isAllNumberAreZero(){
        return allNumAreZero;
    }
}
