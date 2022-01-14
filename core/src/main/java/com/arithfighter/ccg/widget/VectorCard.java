package com.arithfighter.ccg.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class VectorCard {
    float initX;
    float initY;
    float cardX;
    float cardY;
    float cardWidth;
    float cardHeight;
    Color color;
    enum CardState{ACTIVE, INACTIVE}
    CardState state = CardState.INACTIVE;

    public VectorCard(float initX, float initY, float cardSize, Color color) {
        this.initX = initX;
        this.initY = initY;
        this.color = color;
        cardWidth = cardSize;
        cardHeight = cardSize*1.4f;

        cardX = initX;
        cardY = initY;
    }

    public void draw(ShapeRenderer shapeRenderer, float x, float y) {
        checkTouchedDown(shapeRenderer);
        shapeRenderer.setColor(color);
        checkTouchOn(shapeRenderer, x, y);
        checkOutOfWindow();
    }

    private void checkTouchOn(ShapeRenderer shapeRenderer, float x, float y) {
        float movingDis = 15;
        if (isOnCard(x,y))
            shapeRenderer.rect(cardX, cardY+movingDis, cardWidth, cardHeight);
        else
            shapeRenderer.rect(cardX, cardY, cardWidth, cardHeight);
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

    private void checkTouchedDown(ShapeRenderer shapeRenderer) {
        if (isActive())
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        else
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
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
