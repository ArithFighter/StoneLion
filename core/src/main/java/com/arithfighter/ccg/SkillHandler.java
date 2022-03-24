package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.NumberBoxDisplacer;

public class SkillHandler {
    public void cast(CharacterList character, NumberBoxDisplacer numDisplacer){
        switch (character) {
            case KNIGHT:
                //change one value of numberBox
                numDisplacer.set(0, 33);
                break;
            case ROGUE:
                //reduce all values by 1
                for (int i = 0; i < numDisplacer.getNumberBoxQuantity(); i++)
                    if (numDisplacer.getNumberList().get(i) > 0) {
                        numDisplacer.set(
                                i,
                                numDisplacer.getNumberList().get(i) - 1);
                    }
                break;
        }
    }
}
