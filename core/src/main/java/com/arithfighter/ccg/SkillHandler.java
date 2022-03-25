package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.NumberBoxDisplacer;

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
                reduceAllValueByOne();
                break;
            case ROGUE:
                changeANoneZeroValueToSixteen();
                break;
            case HUNTER:
                break;
        }
    }

    private void reduceAllValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (operator.isValueBiggerThanZero(i))
                numberBoxDisplacer.set(i, operator.getNumberBoxValue(i) - 1);
        }
    }

    private void changeANoneZeroValueToSixteen(){
        numberBoxDisplacer.set(operator.getRandomNonZeroValueIndex(), 16);
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
