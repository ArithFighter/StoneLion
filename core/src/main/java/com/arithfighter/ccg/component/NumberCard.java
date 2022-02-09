package com.arithfighter.ccg.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberCard extends RawCard{
    enum CardState {ACTIVE, INACTIVE}
    CardState state = CardState.INACTIVE;
    Sprite card;
    Font text;
    int number;

    public NumberCard(float initX, float initY, Texture texture, int number) {
        setInitPosition(initX, initY);

        configCard(initX, initY, texture.getWidth(), texture.getHeight(), 3);

        this.number = number;

        card = new Sprite(texture);
        card.setColor(Color.GOLDENROD);
        card.setPosition(cardX, cardY);
        card.setSize(cardWidth, cardHeight);

        text = new Font(30);
        text.setColor(Color.WHITE);
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
        drawNumber(batch);
    }

    public void checkTouchingCard(float x, float y) {
        int movingDistance = 15;
        float speed = 2;

        if (isOnCard(x, y)){
            if (cardY < initY + movingDistance)
                cardY += speed;
        }
        else
            resetPosition();
    }

    public void dispose() {
        text.dispose();
    }

    private void drawNumber(SpriteBatch batch) {
        float numberX = cardX + 10;
        float numberY = cardY + cardHeight;

        text.draw(batch, showNumberText(), numberX, numberY);
    }

    private String showNumberText(){
        String content;

        if (number<10 && number!=0)
            content = String.valueOf(number);
        else
            content = "RE"+number;

        return content;
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
        if (isActive())
            updatePosition(x - cardWidth / 2, y - cardHeight / 2);
    }

    public void resetPosition() {
        updatePosition(initX, initY);
        state = CardState.INACTIVE;
    }

    public void checkActive(float mouseX, float mouseY) {
        if (isOnCard(mouseX, mouseY))
            state = CardState.ACTIVE;
    }

    public boolean isActive() {
        return state == CardState.ACTIVE;
    }

    private void updatePosition(float x, float y) {
        cardX = x;
        cardY = y;
    }

    private boolean isOnCard(float x, float y) {
        int tolerance = 20;

        if(isActive()){
            tolerance*=4;
        }
        return x > cardX - tolerance &&
                x < cardX + cardWidth + tolerance &&
                y > cardY - tolerance &&
                y < cardY + cardHeight + tolerance;
    }
}
