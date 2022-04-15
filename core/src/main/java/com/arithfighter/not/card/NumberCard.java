package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Shape;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberCard{
    private final Shape shape;
    private final Sprite sprite;
    private final int number;
    private final StateManager stateManager = new StateManager();
    private final Point initPoint;
    private final Point point;

    public NumberCard(float initX, float initY, Texture texture, int number) {
        RawCard card = new RawCard(initX, initY);
        card.setSize(texture, 1.8f);
        
        initPoint = card.getInitPoint();
        point = card.getPoint();

        this.number = number;
        shape = card.getShape();

        sprite = new Sprite(texture);
        sprite.setPosition(point.getX(), point.getY());
        sprite.setSize(shape.getWidth(), shape.getHeight());
    }

    public int getNumber(){
        return number;
    }

    public float getWidth() {
        return shape.getWidth();
    }

    public void draw(SpriteBatch batch) {
        checkOutOfWindow();

        sprite.setPosition(point.getX(), point.getY());

        if (stateManager.isActive())
            sprite.setSize(shape.getWidth()*1.2f, shape.getHeight()*1.2f);

        sprite.draw(batch);
    }

    public void updateWhenTouchCard(float x, float y) {
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
        float limitX = Gdx.graphics.getWidth() - shape.getWidth();
        float limitY = Gdx.graphics.getHeight() - shape.getHeight();

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
            point.set(x - shape.getWidth() / 2, y - shape.getHeight() / 2);
    }

    public void initCard() {
        point.set(initPoint.getX(), initPoint.getY());

        stateManager.deactivate();

        sprite.setSize(shape.getWidth(), shape.getHeight());
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
                x < point.getX() + shape.getWidth() + tolerance &&
                y > point.getY() - tolerance &&
                y < point.getY() + shape.getHeight() + tolerance;
    }
}

class StateManager {
    private enum State {ACTIVE, INACTIVE}
    private State state = State.INACTIVE;

    public void deactivate(){
        state = State.INACTIVE;
    }

    public void activate(){
        state = State.ACTIVE;
    }

    public boolean isActive(){
        return state == State.ACTIVE;
    }
}
