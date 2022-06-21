package com.arithfighter.not.entity.game;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.system.GameNumProducer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class TabooNumber {
    private final Font font;
    private int value1 = 0;
    private int value2 = 0;
    private Point point;
    private final GameNumProducer gameNumProducer;

    public TabooNumber(Font font) {
        this.font = font;
        gameNumProducer = new GameNumProducer();
    }

    public void setValues(){
        value1 = gameNumProducer.getRandomNum();
        value2 = gameNumProducer.getRandomNum();
        while (value1 == value2)
            value2 = gameNumProducer.getRandomNum();
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, value1 + " " + value2, point.getX(), point.getY());
    }

    public void update(int sum) {
        if (sum == value1)
            value1 = 0;
        if (sum == value2)
            value2 = 0;
    }

    public boolean isViolatingTaboos() {
        return value1 == 0 && value2 == 0;
    }
}
