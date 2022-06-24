package com.arithfighter.not.entity.sumbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class SumBoxEntity {
    private final SumBox sumBox;
    private final SumBoxModel sumBoxModel;
    private final Point point;
    private boolean isReadyToResetSum = false;

    public SumBoxEntity(Texture texture, Font font) {
        sumBox = new SumBox(texture);
        point = new Point(CENTER_X + GRID_X * 6, GRID_Y * 11);
        sumBox.setPosition(point.getX(), point.getY());
        sumBox.setFont(font);

        sumBoxModel = new SumBoxModel();
    }

    public Point getPoint() {
        return point;
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
        isReadyToResetSum = false;
    }

    public void draw(SpriteBatch batch) {
        sumBox.setCapacity(sumBoxModel.getCardCapacity());
        sumBox.draw(sumBoxModel.getSum(), batch);
    }

    public void touchDown(){
        //reset sum will happen after you touch screen
        if (isReadyToResetSum)
            init();
    }

    public void touchUp(){
        if (isCapacityFull())
            isReadyToResetSum = true;
    }
}
