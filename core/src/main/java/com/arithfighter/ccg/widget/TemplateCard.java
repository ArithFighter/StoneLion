package com.arithfighter.ccg.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TemplateCard {
    float initX;
    float initY;
    float cardX;
    float cardY;
    float cardWidth;
    float cardHeight;
    Color color;
    enum CardState{ACTIVE, INACTIVE}
    CardState state = CardState.INACTIVE;
    Sprite card;

    public TemplateCard(float initX, float initY, float scale, Color color, Texture texture) {
        this.initX = initX;
        this.initY = initY;
        this.color = color;
        cardWidth = texture.getWidth()*scale;
        cardHeight = texture.getHeight()*scale;
        cardX = initX;
        cardY = initY;

        card = new Sprite(texture);
        card.setColor(color);
        card.setPosition(initX,initY);
        card.setSize(cardWidth,cardHeight);
    }

    public float getWidth(){
        return cardWidth;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        checkTouchOn(batch, x, y);
        checkOutOfWindow();
    }

    private void checkTouchOn(SpriteBatch batch, float x, float y) {
        float movingDis = 15;
        if (isOnCard(x,y))
            card.setPosition(cardX,cardY+movingDis);
        else
            card.setPosition(cardX,cardY);
        card.draw(batch);
    }

    private void checkOutOfWindow(){
        float limitX = Gdx.graphics.getWidth() - cardWidth;
        float limitY = Gdx.graphics.getHeight() - cardHeight;

        if (cardX > limitX) {
            cardX = limitX;
        }
        if (cardX < 0) {
            cardX = 0;
        }
        if (cardY > limitY) {
            cardY = limitY;
        }
        if (cardY < 0) {
            cardY = 0;
        }
    }

    public void updateWhenDrag(float x, float y) {
        if (isActive())
            updatePosition(x - cardWidth / 2,y - cardHeight / 2);
    }

    public void resetCard(){
        updatePosition(initX, initY);
        state = CardState.INACTIVE;
    }

    public void checkActive(float mouseX, float mouseY){
        if (isOnCard(mouseX, mouseY))
            state = CardState.ACTIVE;
    }

    public boolean isActive(){
        return state == CardState.ACTIVE;
    }

    private void updatePosition(float x, float y){
        cardX = x;
        cardY = y;
    }

    private boolean isOnCard(float x, float y) {
        return x > cardX && x < cardX + cardWidth &&
                y > cardY && y < cardY + cardHeight;
    }
}
