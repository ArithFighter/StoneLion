package com.arithfighter.ccg.card;

import com.arithfighter.ccg.widget.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberCard{
    private final SpriteCard card;
    private final Sprite sprite;
    private final int number;
    private final StateManager stateManager = new StateManager();
    private final Point initPoint;
    private final Point point;

    public NumberCard(float initX, float initY, Texture texture, int number) {
        card = new SpriteCard(initX, initY);
        card.setSize(texture, 1.8f);
        
        initPoint = card.getInitPoint();
        point = card.getPoint();

        this.number = number;

        sprite = new Sprite(texture);
        sprite.setPosition(point.getX(), point.getY());
        sprite.setSize(card.getWidth(), card.getHeight());
    }

    public int getNumber(){
        return number;
    }

    public float getWidth() {
        return card.getWidth();
    }

    public void draw(SpriteBatch batch) {
        checkOutOfWindow();

        sprite.setPosition(point.getX(), point.getY());

        if (stateManager.isActive())
            sprite.setSize(card.getWidth()*1.2f, card.getHeight()*1.2f);

        sprite.draw(batch);
    }

    public void checkTouchingCard(float x, float y) {
        if (isOnCard(x, y)){
            playTouchedAnimation();
        }
        else
            initCard();
    }

    private void playTouchedAnimation(){
        int movingDistance = 30;
        float speed = 3;
        if (point.getY() < initPoint.getY() + movingDistance)
            point.setY(point.getY()+speed);
    }

    private void checkOutOfWindow() {
        float limitX = Gdx.graphics.getWidth() - card.getWidth();
        float limitY = Gdx.graphics.getHeight() - card.getHeight();

        point.set(updateWhenOutOfWindow(point.getX(), limitX),
                updateWhenOutOfWindow(point.getY(), limitY));
    }

    private float updateWhenOutOfWindow(float current, float limit) {
        int minimum = -45;
        if (current > limit)
            current = limit;

        if (current < minimum)
            current = minimum;

        return current;
    }

    public void updateWhenDrag(float x, float y) {
        if (stateManager.isActive())
            point.set(x - card.getWidth() / 2, y - card.getHeight() / 2);
    }

    public void initCard() {
        point.set(initPoint.getX(), initPoint.getY());

        stateManager.setInactive();

        sprite.setSize(card.getWidth(), card.getHeight());
    }

    public void activateCard(float mouseX, float mouseY) {
        if (isOnCard(mouseX, mouseY))
            stateManager.activate();
    }

    public boolean isActive(){
        return stateManager.isActive();
    }

    private boolean isOnCard(float x, float y) {
        int tolerance = 15;

        if(stateManager.isActive()){
            tolerance*=4;
        }
        return x > point.getX() - tolerance &&
                x < point.getX() + card.getWidth() + tolerance &&
                y > point.getY() - tolerance &&
                y < point.getY() + card.getHeight() + tolerance;
    }
}
