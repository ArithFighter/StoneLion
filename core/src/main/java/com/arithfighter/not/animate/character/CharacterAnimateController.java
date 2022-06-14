package com.arithfighter.not.animate.character;

import com.arithfighter.not.pojo.Point;

public class CharacterAnimateController {
    private final CharacterAnimatable[] characterAnimatable;

    public CharacterAnimatable[] getCharacterAnimatable() {
        return characterAnimatable;
    }

    public CharacterAnimateController(CharacterAnimatable[] characterAnimatable) {
        this.characterAnimatable = characterAnimatable;
    }

    public void setScale(int scale){
        for (CharacterAnimatable ga: characterAnimatable)
            ga.getAnimation().setScale(scale);
    }

    public void setDrawPoint(Point point){
        for (CharacterAnimatable ga: characterAnimatable)
            ga.getAnimation().setDrawPoint(point);
    }

    public boolean isAllActionEnd(){
        boolean condition = false;
        int x = 0;

        for (CharacterAnimatable ga: characterAnimatable){
            if (ga.getAnimation().isEnd())
                x++;
        }
        if (x == characterAnimatable.length)
            condition = true;

        return condition;
    }

    public void init(){
        for (CharacterAnimatable ga: characterAnimatable)
            ga.getAnimation().init();
    }
}
