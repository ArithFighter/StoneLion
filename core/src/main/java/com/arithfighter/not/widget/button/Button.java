package com.arithfighter.not.widget.button;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.DetectableFontWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button{
    private final Font font;
    private final DetectableFontWidget widget;

    private enum State{ON, OFF}
    private State buttonState = State.OFF;

    public Button(Texture texture, float scale){
        widget = new SpriteWidget(texture, scale, 22);

        font = new Font(widget.getFontSize());
    }

    public void setPosition(float x, float y){
        widget.setPosition(x, y);
    }

    public void draw(SpriteBatch batch, String content){
        if (isActive())
            changeColor(batch, content);
        else
            initialize(batch, content);
    }

    private void initialize(SpriteBatch batch, String content){
        widget.getSprite().setColor(Color.WHITE);
        font.setColor(Color.BLACK);
        show(batch, content);
    }

    private void changeColor(SpriteBatch batch, String content){
        widget.getSprite().setColor(Color.ORANGE);
        font.setColor(Color.WHITE);
        show(batch, content);
    }

    private void show(SpriteBatch batch, String content){
        widget.draw(batch);
        font.draw(
                batch,
                content,
                widget.getCenterX(content),
                widget.getCenterY()
        );
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

    public void dispose(){
        font.dispose();
    }
}
