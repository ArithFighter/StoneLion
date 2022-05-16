package com.arithfighter.not.widget.button;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.DetectableFontWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button implements Clickable{
    private final Font font;
    private final DetectableFontWidget button;

    private enum State{ON, OFF}
    private State buttonState = State.OFF;

    public Button(Texture texture, float scale){
        button = new SpriteWidget(texture, scale, 22);

        font = new Font(button.getFontSize());
    }

    public DetectableFontWidget getButton(){
        return button;
    }

    public void setPosition(float x, float y){
        button.setPosition(x, y);
    }

    public void draw(SpriteBatch batch, String content){
        if (isOn())
            changeColor(batch, content);
        else
            initialize(batch, content);
    }

    private void initialize(SpriteBatch batch, String content){
        button.getSprite().setColor(Color.WHITE);
        font.setColor(Color.BLACK);
        show(batch, content);
    }

    private void changeColor(SpriteBatch batch, String content){
        button.getSprite().setColor(Color.ORANGE);
        font.setColor(Color.WHITE);
        show(batch, content);
    }

    private void show(SpriteBatch batch, String content){
        button.draw(batch);
        font.draw(
                batch,
                content,
                button.getCenterX(content),
                button.getCenterY()
        );
    }

    private boolean isOnButton(float x, float y){
        return button.isOnWidget(x,y);
    }

    public void on(float x, float y){
        if (isOnButton(x, y))
            buttonState = State.ON;
    }

    public boolean isOn(){
        return buttonState == State.ON;
    }

    public void off(){
        buttonState = State.OFF;
    }

    public void dispose(){
        font.dispose();
    }
}
