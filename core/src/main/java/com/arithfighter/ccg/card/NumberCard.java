package com.arithfighter.ccg.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberCard extends RawCard {
    Sprite card;
    int number;
    StateManager stateManager = new StateManager();

    public NumberCard(float initX, float initY, Texture texture, int number) {
        setInitPosition(initX, initY);

        configCard(initX, initY, texture.getWidth(), texture.getHeight(), 1.8f);

        this.number = number;

        card = new Sprite(texture);
        card.setPosition(cardX, cardY);
        card.setSize(cardWidth, cardHeight);
    }

    public int getNumber(){
        return number;
    }

    public float getWidth() {
        return cardWidth;
    }

    public void draw(SpriteBatch batch) {
        checkOutOfWindow();
        card.setPosition(cardX, cardY);
        card.draw(batch);
    }

    public void checkTouchingCard(float x, float y) {
        if (isOnCard(x, y))
            playTouchedAnimation();
        else
            resetPosition();
    }

    private void playTouchedAnimation(){
        int movingDistance = 30;
        float speed = 3;
        if (cardY < initY + movingDistance)
            cardY += speed;
    }

    private void checkOutOfWindow() {
        float limitX = Gdx.graphics.getWidth() - cardWidth;
        float limitY = Gdx.graphics.getHeight() - cardHeight;

        cardX = updateWhenOutOfWindow(cardX, limitX);
        cardY = updateWhenOutOfWindow(cardY, limitY);
    }

    private float updateWhenOutOfWindow(float current, float limit) {
        int minimum = -45;
        if (current > limit) current = limit;
        if (current < minimum) current = minimum;
        return current;
    }

    public void updateWhenDrag(float x, float y) {
        if (stateManager.isActive())
            updatePosition(x - cardWidth / 2, y - cardHeight / 2);
    }

    public void resetPosition() {
        updatePosition(initX, initY);
        stateManager.inActive();
    }

    public void checkActive(float mouseX, float mouseY) {
        if (isOnCard(mouseX, mouseY))
            stateManager.active();
    }

    private void updatePosition(float x, float y) {
        cardX = x;
        cardY = y;
    }

    public boolean isActive(){
        return stateManager.isActive();
    }

    private boolean isOnCard(float x, float y) {
        int tolerance = 15;

        if(stateManager.isActive()){
            tolerance*=4;
        }
        return x > cardX - tolerance &&
                x < cardX + cardWidth + tolerance &&
                y > cardY - tolerance &&
                y < cardY + cardHeight + tolerance;
    }
}
