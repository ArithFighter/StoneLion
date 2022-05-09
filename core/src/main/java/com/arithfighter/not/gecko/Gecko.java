package com.arithfighter.not.gecko;

import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gecko {
    private final DetectableWidget widget;
    private final int scale = 8;

    public Gecko(Texture texture){
        widget = new SpriteWidget(texture, scale);
    }

    public int getScale(){
        return scale;
    }

    public void setPosition(float x, float y){
        widget.setPosition(x,y);
    }

    public void draw(SpriteBatch batch){
        widget.draw(batch);
    }

    public final void playCardToBasket(int mouseX, int mouseY) {
        if (isOnBasket(mouseX, mouseY)) {
            checkCardPlayed();
        }
        initCardPosition();
    }

    public void initCardPosition() {

    }

    public void checkCardPlayed() {

    }

    public boolean isOnBasket(float x, float y){
        return widget.isOnWidget(x,y);
    }
}
