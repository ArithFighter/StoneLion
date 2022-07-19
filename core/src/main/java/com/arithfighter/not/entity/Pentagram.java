package com.arithfighter.not.entity;

import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.button.PanelButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pentagram {
    private final VisibleWidget pentagram;
    private final PlaceMarkCollection mark;
    private Point point;

    public Pentagram(Texture[] textures, float scale) {
        pentagram = new SpriteWidget(textures[0], scale);

        mark = new PlaceMarkCollection(textures[1], 1.5f);

        LayoutSetter layoutSetter = new LayoutSetter(new Rectangle(
                pentagram.getWidget().getWidth(),
                pentagram.getWidget().getHeight()
        ));
        layoutSetter.setGrid(7, 7);
        mark.setGrid(layoutSetter.getGrid());
    }

    public void setPoint(Point point) {
        this.point = new Point(point.getX() - pentagram.getWidget().getWidth() / 2, point.getY());

        mark.setInitPoint(this.point);
    }

    public void draw(SpriteBatch batch) {
        pentagram.setPosition(point.getX(), point.getY());
        pentagram.draw(batch);

        mark.draw(batch);
    }
}

enum EnchantmentLevel {
    NONE(Color.GRAY, 0, 0),
    LOW(Color.GREEN, 1, 3),
    MID(Color.BLUE, 4, 6),
    HIGH(Color.PURPLE, 7, 9);

    private final Color color;
    private final int minBell;
    private final int maxBell;

    EnchantmentLevel(Color color, int minBell, int maxBell) {
        this.color = color;
        this.minBell = minBell;
        this.maxBell = maxBell;
    }

    public Color getColor() {
        return color;
    }

    public int getMinBell() {
        return minBell;
    }

    public int getMaxBell() {
        return maxBell;
    }
}

class PlaceMarkCollection {
    private final PlaceMark[] placeMarks;
    private Point initPoint;
    private Rectangle grid;
    private final PlaceMarkPlacer placeMarkPlacer;

    public PlaceMarkCollection(Texture texture, float scale) {
        placeMarks = new PlaceMark[6];

        for (int i = 0; i < placeMarks.length; i++) {
            placeMarks[i] = new PlaceMark(texture, scale);
            placeMarks[i].setLevel(EnchantmentLevel.MID);
        }

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
}

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

class PlaceMark {
    private final DetectableWidget mark;
    private EnchantmentLevel level;
    private Point point;
    private boolean isOn = false;

    public PlaceMark(Texture texture, float scale) {
        mark = new SpriteWidget(texture, scale);
    }

    public void setLevel(EnchantmentLevel level) {
        this.level = level;
    }

    public EnchantmentLevel getLevel() {
        return level;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch) {
        mark.getSprite().setColor(level.getColor());
        mark.setPosition(point.getX(), point.getY());
        mark.draw(batch);
    }

    public void on(float x, float y){
        if (mark.isOnWidget(x,y))
            isOn = true;
    }

    public boolean isOn(){
        return isOn;
    }

    public void off(){
        isOn = false;
    }
}