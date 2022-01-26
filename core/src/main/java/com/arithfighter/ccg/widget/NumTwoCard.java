package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class NumTwoCard {
    float initX;
    float initY;
    Texture texture;
    int number = 2;
    NumberCard num2Card;

    public NumTwoCard(float initX, float initY, Texture texture) {
        this.initX = initX;
        this.initY = initY;
        this.texture = texture;

        num2Card = new NumberCard(initX, initY, Color.GREEN, texture, number);
    }

    public NumberCard getCard(){
        return num2Card;
    }
}
