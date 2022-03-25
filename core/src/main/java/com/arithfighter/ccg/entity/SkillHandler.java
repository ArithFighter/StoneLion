package com.arithfighter.ccg.entity;

import java.util.ArrayList;

public class SkillHandler {
    NumberBoxDisplacer numberBoxDisplacer;
    NumBoxOperator operator;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;

        operator = new NumBoxOperator(this.numberBoxDisplacer);
    }

    public void cast(CharacterList character){
        switch (character) {
            case KNIGHT:
                increaseOneNonZeroValueBySix();
                break;
            case ROGUE:
                replaceOneNonZeroValue(16);
                break;
            case HUNTER:
                reduceAllNonZeroValueByOne();
                break;
            case PALADIN:
                replaceOneNonZeroValue(31);
                break;
            case WARRIOR:
                increaseAllNonZeroValueByOne();
                break;
        }
    }

    private void increaseAllNonZeroValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (operator.isValueBiggerThanZero(i))
                numberBoxDisplacer.set(i, operator.getNumberBoxValue(i) + 1);
        }
    }

    private void reduceAllNonZeroValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (operator.isValueBiggerThanZero(i))
                numberBoxDisplacer.set(i, operator.getNumberBoxValue(i) - 1);
        }
    }

    private void increaseOneNonZeroValueBySix(){
        int index = operator.getRandomNonZeroValueIndex();
        numberBoxDisplacer.set(index, operator.getNumberBoxValue(index)+6);
    }

    private void replaceOneNonZeroValue(int value){
        int index = operator.getRandomNonZeroValueIndex();
        numberBoxDisplacer.set(index, value);
    }
}

class NumBoxOperator{
    NumberBoxDisplacer numberBoxDisplacer;

    public NumBoxOperator(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public int getRandomNonZeroValueIndex(){
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (isValueBiggerThanZero(i))
                indexList.add(i);
        }

        int indexPick = getRandomNum(indexList.size());

        return indexList.get(indexPick);
    }

    public boolean isValueBiggerThanZero(int i){
        return getNumberBoxValue(i) > 0;
    }

    public int getNumberBoxValue(int i){
        return numberBoxDisplacer.getNumberList().get(i);
    }

    private int getRandomNum(int max){
        return (int)(Math.random() * (max + 1));
    }
}
