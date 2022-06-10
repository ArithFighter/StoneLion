package com.arithfighter.not.entity.sumbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class SumBoxEntity {
    private final SumBox sumBox;
    private final SumBoxModel sumBoxModel;

    public SumBoxEntity(Texture texture, Font font) {
        sumBox = new SumBox(texture);
        Point sumPoint = new Point(CENTER_X + GRID_X * 6, GRID_Y * 11);
        sumBox.setPosition(sumPoint.getX(), sumPoint.getY());
        sumBox.setFont(font);

        sumBoxModel = new SumBoxModel();
    }

    public SumBoxModel getSumBoxModel() {
        return sumBoxModel;
    }

    public boolean isCapacityWarning() {
        return sumBox.isCapacityWarning();
    }

    public boolean isCapacityFull() {
        return sumBoxModel.isCapacityFull();
    }

    public void init() {
        sumBox.init();
        sumBoxModel.init();
    }

    public void draw(SpriteBatch batch) {
        sumBox.setCapacity(sumBoxModel.getCardCapacity());
        sumBox.draw(sumBoxModel.getSum(), batch);
    }
}
