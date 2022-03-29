package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PanelButton {
    private final SpriteWidget widget;
    private enum State{ON, OFF}
    private State buttonState = State.OFF;

    public PanelButton(Texture texture){
        widget = new SpriteWidget(texture, 0.8f);
    }

    public void setPosition(float x, float y){
        widget.setPosition(x,y);
    }

    public void draw(SpriteBatch batch){
        if (isActive())
            changeColor(batch);
        else
            initialize(batch);
    }

    private void initialize(SpriteBatch batch){
        widget.getSprite().setColor(Color.WHITE);
        widget.draw(batch);
    }

    private void changeColor(SpriteBatch batch){
        widget.getSprite().setColor(Color.ORANGE);
        widget.draw(batch);
    }

    private boolean isOnButton(float x, float y){
        return widget.isOnWidget(x,y);
    }

    public void activate(float x, float y){
        if (isOnButton(x, y))
            buttonState = State.ON;
    }

    public boolean isActive(){
        return buttonState == State.ON;
    }

    public void deactivate(){
        buttonState = State.OFF;
    }
}
