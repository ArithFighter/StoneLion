package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Shape;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberCard{
    private final int number;
    private boolean isCardActive = false;
    private final Shape shape;
    private final Point initPoint;
    private final Point point;
    private final RawCard card;

    public NumberCard(float initX, float initY, Texture texture, int number) {
        CardBuilder cardBuilder = new CardBuilder(initX, initY, texture);
        shape = cardBuilder.getShape();
        initPoint = cardBuilder.getInitPoint();
        point = cardBuilder.getPoint();
        card = cardBuilder.getCard();

        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public float getWidth() {
        return shape.getWidth();
    }

    public void draw(SpriteBatch batch) {
        point.set(updateWhenExceedX(point.getX()),
                updateWhenExceedY(point.getY()));

        card.setSprite();

        if (isCardActive)
            card.getSprite().setSize(shape.getWidth()*1.2f, shape.getHeight()*1.2f);

        card.getSprite().draw(batch);
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

    private float updateWhenExceedX(float current){
        float rightX = Gdx.graphics.getWidth() - shape.getWidth();
        int leftX = -45;

        return getBetweenMaxNMin(current, rightX, leftX);
    }

    private float updateWhenExceedY(float current){
        float topY = Gdx.graphics.getHeight() - shape.getHeight();
        int bottomY = -45;

        return getBetweenMaxNMin(current, topY, bottomY);
    }

    private float getBetweenMaxNMin(float value, float max, float min){
        value = Math.min(value, max);

        value = Math.max(value, min);

        return value;
    }

    public void updateWhenDrag(float x, float y) {
        if (isCardActive)
            point.set(x - shape.getWidth() / 2, y - shape.getHeight() / 2);
    }

    public void initCard() {
        point.set(initPoint.getX(), initPoint.getY());

        isCardActive = false;

        card.getSprite().setSize(shape.getWidth(), shape.getHeight());
    }

    public void activateCard(float mouseX, float mouseY) {
        if (isOnCard(mouseX, mouseY))
            isCardActive =true;
    }

    public boolean isActive(){
        return isCardActive;
    }

    private boolean isOnCard(float x, float y) {
        int tolerance = 15;

        tolerance = getCardActivationRange(tolerance);

        return x > point.getX() - tolerance &&
                x < point.getX() + shape.getWidth() + tolerance &&
                y > point.getY() - tolerance &&
                y < point.getY() + shape.getHeight() + tolerance;
    }

    private int getCardActivationRange(int tolerance){
        if(isCardActive)
            tolerance*=4;
        return tolerance;
    }
}

class CardBuilder{
    private final Shape shape;
    private final Point initPoint;
    private final Point point;
    private final RawCard card;

    public CardBuilder(float initX, float initY, Texture texture){
        card = new RawCard(initX, initY, texture);
        card.addShape(texture, 1.8f);
        initPoint = card.getInitPoint();
        point = card.getPoint();
        shape = card.getShape();
    }

    public Shape getShape() {
        return shape;
    }

    public Point getInitPoint() {
        return initPoint;
    }

    public Point getPoint() {
        return point;
    }

    public RawCard getCard() {
        return card;
    }
}
