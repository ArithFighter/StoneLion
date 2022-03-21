package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
    private final Font font;
    private final Sprite button;
    private final SpriteWidget widget;
    private final Point point;
    private enum State{ON, OFF}
    private State buttonState = State.OFF;

    public Button(Texture texture){
        widget = new SpriteWidget(25);
        widget.setSize(texture, 2f);
        point = widget.getPoint();

        font = new Font(widget.getFontSize());

        button = new Sprite(texture);
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    private void setupButton(){
        button.setSize(widget.getWidth(), widget.getHeight());
        button.setPosition(point.getX(), point.getY());
    }

    public void draw(SpriteBatch batch, String content){
        setupButton();
        if (isActive())
            hover(batch, content);
        else
            initialize(batch, content);
    }

    private void initialize(SpriteBatch batch, String content){
        button.setColor(Color.WHITE);
        font.setColor(Color.BLACK);
        show(batch, content);
    }

    private void hover(SpriteBatch batch, String content){
        button.setColor(Color.ORANGE);
        font.setColor(Color.WHITE);
        show(batch, content);
    }

    private void show(SpriteBatch batch, String content){
        button.draw(batch);
        font.draw(
                batch,
                content,
                widget.getCenterX(content),
                widget.getCenterY()
        );
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

    private boolean isActive(){
        return buttonState == State.ON;
    }

    public void deactivate(){
        buttonState = State.OFF;
    }

    public void dispose(){
        font.dispose();
    }
}
