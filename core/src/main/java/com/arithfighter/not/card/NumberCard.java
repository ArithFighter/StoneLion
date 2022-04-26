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
        card = new RawCard(initX, initY, texture);
        card.addShape(texture, 2f);
        initPoint = card.getInitPoint();
        point = card.getPoint();
        shape = card.getShape();

        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public Shape getShape() {
        return shape;
    }

    public Point getPoint(){
        return point;
    }

    public Point getInitPoint(){
        return initPoint;
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    public void draw(SpriteBatch batch) {
        setPointWhenOutOfScreen();

        card.setSprite();

        if (isCardActive)
            card.getSprite().setSize(shape.getWidth()*1.2f, shape.getHeight()*1.2f);

        card.getSprite().draw(batch);
    }

    private void setPointWhenOutOfScreen(){
        ScreenBorderInspector sbi = new ScreenBorderInspector(shape.getWidth(), shape.getHeight());

        point.set(sbi.updateWhenExceedX(point.getX()),
                sbi.updateWhenExceedY(point.getY()));
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

    public boolean isOnCard(float x, float y) {
        int tolerance = 10;

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

class ScreenBorderInspector{
    private final float objectWidth;
    private final float objectHeight;

    public ScreenBorderInspector(float objectWidth, float objectHeight){
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;
    }

    public float updateWhenExceedX(float current){
        float rightX = Gdx.graphics.getWidth() - objectWidth;
        int leftX = -45;

        return getBetweenMaxNMin(current, rightX, leftX);
    }

    public float updateWhenExceedY(float current){
        float topY = Gdx.graphics.getHeight() - objectHeight;
        int bottomY = -45;

        return getBetweenMaxNMin(current, topY, bottomY);
    }

    private float getBetweenMaxNMin(float value, float max, float min){
        value = Math.min(value, max);

        value = Math.max(value, min);

        return value;
    }
}
