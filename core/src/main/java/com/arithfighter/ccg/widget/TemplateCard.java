package com.arithfighter.ccg.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TemplateCard {
    float initX, initY;
    float cardX, cardY, cardWidth, cardHeight;
    float scale = 4;
    enum CardState {ACTIVE, INACTIVE}
    CardState state = CardState.INACTIVE;
    Sprite card;
    Font text;
    String number;

    public TemplateCard(float initX, float initY, Color color, Texture texture, String number) {
        this.initX = initX;
        this.initY = initY;
        cardWidth = texture.getWidth() * scale;
        cardHeight = texture.getHeight() * scale;
        cardX = initX;
        cardY = initY;
        this.number = number;

        card = new Sprite(texture);
        card.setColor(color);
        card.setPosition(initX, initY);
        card.setSize(cardWidth, cardHeight);

        text = new Font(30);
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
            resetCard();
    }

    public void dispose() {
        text.dispose();
    }

    private void drawNumber(SpriteBatch batch) {
        float numberX = cardX + 10;
        float numberY = cardY + cardHeight;
        text.setColor(Color.YELLOW);
        text.draw(batch, number, numberX, numberY);
    }

    private void checkOutOfWindow() {
        float limitX = Gdx.graphics.getWidth() - cardWidth;
        float limitY = Gdx.graphics.getHeight() - cardHeight;

        cardX = updateWhenOutOfWindow(cardX, limitX);
        cardY = updateWhenOutOfWindow(cardY, limitY);
    }

    private float updateWhenOutOfWindow(float current, float limit) {
        if (current > limit) current = limit;
        if (current < 0) current = 0;
        return current;
    }

    public void updateWhenDrag(float x, float y) {
        if (isActive())
            updatePosition(x - cardWidth / 2, y - cardHeight / 2);
    }

    public void resetCard() {
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
        int tolerance = 25;
        return x > cardX - tolerance &&
                x < cardX + cardWidth + tolerance &&
                y > cardY - tolerance &&
                y < cardY + cardHeight + tolerance;
    }
}
