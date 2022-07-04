package com.arithfighter.not.card;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NumberCard{
    private final int number;
    private boolean isCardActive = false;
    private final Rectangle rectangle;
    private final RawCard card;

    public NumberCard(TextureRegion texture, int number, int scale) {
        card = new RawCard(texture, scale);

        rectangle = card.getShape();

        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public Rectangle getShape() {
        return rectangle;
    }

    public Point getPoint(){
        return card.getPoint();
    }

    public Point getInitPoint(){
        return card.getInitPoint();
    }

    public void draw(SpriteBatch batch) {
        setPointWhenOutOfScreen();

        card.setSprite();

        if (isCardActive)
            card.getSprite().setSize(rectangle.getWidth()*1.2f, rectangle.getHeight()*1.2f);

        card.getSprite().draw(batch);
    }

    private void setPointWhenOutOfScreen(){
        ScreenBorderInspector sbi = new ScreenBorderInspector(rectangle.getWidth(), rectangle.getHeight());

        getPoint().set(sbi.updateWhenExceedX(getPoint().getX()),
                sbi.updateWhenExceedY(getPoint().getY()));
    }

    public void initCard() {
        getPoint().set(getInitPoint().getX(), getInitPoint().getY());

        isCardActive = false;

        card.getSprite().setSize(rectangle.getWidth(), rectangle.getHeight());
    }

    public void activateCard(float mouseX, float mouseY) {
        if (isOnCard(mouseX, mouseY))
            isCardActive = true;
    }

    public boolean isActive(){
        return isCardActive;
    }

    public boolean isOnCard(float x, float y) {
        int tolerance = 10;

        tolerance = getCardActivationRange(tolerance);

        Point point = getPoint();

        return x > point.getX() - tolerance &&
                x < point.getX() + rectangle.getWidth() + tolerance &&
                y > point.getY() - tolerance &&
                y < point.getY() + rectangle.getHeight() + tolerance;
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
