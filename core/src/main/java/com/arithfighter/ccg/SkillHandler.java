package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.NumberBoxDisplacer;

import java.util.ArrayList;

public class SkillHandler {
    NumberBoxDisplacer numberBoxDisplacer;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public void cast(CharacterList character){
        switch (character) {
            case KNIGHT:
                reduceAllValueByOne();
                break;
            case ROGUE:
                changeANoneZeroValueToSixteen();
                break;
        }
    }

    private void reduceAllValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){

            if (isValueBiggerThanZero(i))
                numberBoxDisplacer.set(i, getNumberBoxValue(i) - 1);
        }
    }

    private void changeANoneZeroValueToSixteen(){
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (isValueBiggerThanZero(i))
                indexList.add(i);
        }

        int indexPick = getRandomNum(indexList.size());

        numberBoxDisplacer.set(indexList.get(indexPick), 16);
    }

    private boolean isValueBiggerThanZero(int i){
        return getNumberBoxValue(i) > 0;
    }

    private int getNumberBoxValue(int i){
        return numberBoxDisplacer.getNumberList().get(i);
    }

    private int getRandomNum(int max){
        return (int)(Math.random() * (max + 1));
    }
}
