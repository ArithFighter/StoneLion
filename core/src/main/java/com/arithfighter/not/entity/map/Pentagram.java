package com.arithfighter.not.entity.map;

import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pentagram {
    private final VisibleWidget pentagram;
    private final VisibleWidget highLight;
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

        highLight = new SpriteWidget(textures[2], 0.8f);
    }

    public void setPoint(Point point) {
        this.point = new Point(point.getX() - pentagram.getWidget().getWidth() / 2, point.getY());

        mark.setInitPoint(this.point);
    }

    public boolean isOn(){
        return mark.isOn();
    }

    public void on(float x, float y){
        mark.on(x,y);
    }

    public void off(){
        mark.off();
    }

    public void draw(SpriteBatch batch) {
        pentagram.setPosition(point.getX(), point.getY());
        pentagram.draw(batch);

        mark.draw(batch);

        try {
            Point markP = getSelectedPlaceMark().getPoint();

            highLight.setPosition(markP.getX()-5, markP.getY()-5);
        }catch (NullPointerException ignored){}

        highLight.draw(batch);
    }

    public PlaceMark getSelectedPlaceMark(){
        return mark.getPlaceMarks()[mark.getSelectedIndex()];
    }
}