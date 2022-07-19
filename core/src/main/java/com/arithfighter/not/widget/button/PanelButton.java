package com.arithfighter.not.widget.button;

import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PanelButton{
    private final DetectableWidget button;
    private boolean isOn = false;

    public PanelButton(Texture texture, float scale){
        button = new SpriteWidget(texture, scale);
    }

    public void reverseHorizontal(){
        button.getSprite().flip(true, false);
    }

    public void setPosition(float x, float y){
        button.setPosition(x,y);
    }

    public void draw(SpriteBatch batch){
        if (isOn())
            changeColor(batch);
        else
            initialize(batch);
    }

    private void initialize(SpriteBatch batch){
        button.getSprite().setColor(Color.WHITE);
        button.draw(batch);
    }

    private void changeColor(SpriteBatch batch){
        button.getSprite().setColor(Color.ORANGE);
        button.draw(batch);
    }

    private boolean isOnButton(float x, float y){
        return button.isOnWidget(x,y);
    }

    public void onWhenIsOnButton(float x, float y){
        if (isOnButton(x,y))
            isOn = true;
    }

    public boolean isOn(){
        return isOn;
    }

    public void off(){
        isOn = false;
    }
}
