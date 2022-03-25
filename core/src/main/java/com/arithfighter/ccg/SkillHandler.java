package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.NumberBoxDisplacer;

public class SkillHandler {
    NumberBoxDisplacer numberBoxDisplacer;
    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public void cast(CharacterList character){
        switch (character) {
            case KNIGHT:
                //change one value of numberBox
                numberBoxDisplacer.set(0, 33);
                break;
            case ROGUE:
                reduceAllValueByOne();
                break;
        }
    }

    private int getNumberBoxValue(int i){
        return numberBoxDisplacer.getNumberList().get(i);
    }

    private void reduceAllValueByOne(){
        int quantity = numberBoxDisplacer.getNumberBoxQuantity();
        for (int i = 0; i < quantity; i++)
                    if (getNumberBoxValue(i) > 0) {
                        numberBoxDisplacer.set(
                                i,
                                getNumberBoxValue(i) - 1);
                    }
    }
}
