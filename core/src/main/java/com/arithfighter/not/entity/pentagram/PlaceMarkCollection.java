package com.arithfighter.not.entity.pentagram;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class PlaceMarkCollection {
    private final PlaceMark[] placeMarks;
    private Point initPoint;
    private Rectangle grid;
    private final PlaceMarkPlacer placeMarkPlacer;
    private int selectedIndex = 0;

    public PlaceMarkCollection(Texture texture, float scale) {
        placeMarks = new PlaceMark[6];

        for (int i = 0; i < placeMarks.length; i++)
            placeMarks[i] = new PlaceMark(texture, scale);

        placeMarkPlacer = new PlaceMarkPlacer();
    }

    public PlaceMark[] getPlaceMarks() {
        return placeMarks;
    }

    public void setGrid(Rectangle grid) {
        this.grid = grid;
    }

    public void setInitPoint(Point point) {
        initPoint = point;
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < placeMarks.length; i++) {
            placeMarkPlacer.setInitPoint(initPoint);
            placeMarkPlacer.setGrid(grid);
            placeMarks[i].setPoint(placeMarkPlacer.getPoint(i));
        }

        for (PlaceMark placeMark : placeMarks) {
            if (placeMark != null)
                placeMark.draw(batch);
        }
    }

    public int getSelectedIndex() {
        for (int i = 0; i < placeMarks.length; i++) {
            if (placeMarks[i].isOn())
                selectedIndex = i;
        }
        return selectedIndex;
    }

    public void init() {
        selectedIndex = 0;
    }

    public boolean isOn() {
        boolean b = false;
        for (PlaceMark p : placeMarks) {
            if (p.isOn()) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void on(float x, float y) {
        for (PlaceMark p : placeMarks)
            p.on(x, y);
    }

    public void off() {
        for (PlaceMark p : placeMarks)
            p.off();
    }
}
