package com.arithfighter.not.entity.sumbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumDisplacerEntity {
    private final SumDisplacer sumDisplacer;
    private final SumDisplacerModel sumDisplacerModel;
    private final Point point;
    private boolean isReadyToResetSum = false;

    public SumDisplacerEntity(Texture texture, Font font) {
        sumDisplacer = new SumDisplacer(texture, 3);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(9,9);
        Rectangle grid = layoutSetter.getGrid();

        point = new Point(grid.getWidth()*7, grid.getHeight()*4);
        sumDisplacer.setPosition(point.getX(), point.getY());
        sumDisplacer.setFont(font);

        sumDisplacerModel = new SumDisplacerModel();
    }

    public Point getPoint() {
        return point;
    }

    public SumDisplacerModel getSumBoxModel() {
        return sumDisplacerModel;
    }

    public boolean isCapacityWarning() {
        return sumDisplacer.isCapacityWarning();
    }

    public boolean isCapacityFull() {
        return sumDisplacerModel.isCapacityFull();
    }

    public void init() {
        sumDisplacer.init();
        sumDisplacerModel.init();
        isReadyToResetSum = false;
    }

    public void draw(SpriteBatch batch) {
        sumDisplacer.setCapacity(sumDisplacerModel.getCardCapacity());
        sumDisplacer.draw(sumDisplacerModel.getSum(), batch);
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
