package com.arithfighter.not.entity.sumbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBoxEntity {
    private final SumBox sumBox;
    private final SumBoxModel sumBoxModel;
    private final Point point;
    private boolean isReadyToResetSum = false;

    public SumBoxEntity(Texture texture, Font font) {
        sumBox = new SumBox(texture, 2);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(9,9);
        Rectangle grid = layoutSetter.getGrid();

        point = new Point(grid.getWidth()*7, grid.getHeight()*6);
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
