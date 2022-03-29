package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PanelButton {
    private final Sprite button;
    private final SpriteWidget widget;
    private final Point point;
    private enum State{ON, OFF}
    private State buttonState = State.OFF;

    public PanelButton(Texture texture){
        widget = new SpriteWidget(22);
        widget.setSize(texture, 1.8f);
        point = widget.getPoint();

        button = new Sprite(texture);
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    private void setupButton(){
        button.setSize(widget.getWidth(), widget.getHeight());
        button.setPosition(point.getX(), point.getY());
    }

    public void draw(SpriteBatch batch){
        setupButton();
        if (isActive())
            changeColor(batch);
        else
            initialize(batch);
    }

    private void initialize(SpriteBatch batch){
        button.setColor(Color.WHITE);
        button.draw(batch);
    }

    private void changeColor(SpriteBatch batch){
        button.setColor(Color.ORANGE);
        button.draw(batch);
    }

    private boolean isOnButton(float x, float y){
        return x> point.getX()&&
                x< point.getX()+ widget.getWidth()&&
                y> point.getY()&&
                y< point.getY()+ widget.getHeight();
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
