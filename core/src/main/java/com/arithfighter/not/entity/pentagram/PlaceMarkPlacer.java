package com.arithfighter.not.entity.pentagram;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;

class PlaceMarkPlacer {
    private Point initPoint;
    private Rectangle grid;

    public void setInitPoint(Point initPoint) {
        this.initPoint = initPoint;
    }

    public void setGrid(Rectangle grid) {
        this.grid = grid;
    }

    public Point getPoint(int i) {
        Point point = new Point();

        if (i == 0)
            point.set(
                    initPoint.getX() + grid.getWidth() * 3,
                    initPoint.getY() + grid.getHeight() * 5
            );
        else if (i <= 3)
            point.set(
                    initPoint.getX() + grid.getWidth() * 2 * (i - 0.5f),
                    initPoint.getY() + grid.getHeight() * 3
            );
        else
            point.set(
                    initPoint.getX() + grid.getWidth() * 2 * (i - 3),
                    initPoint.getY() + grid.getHeight() * 1
            );

        return point;
    }
}
