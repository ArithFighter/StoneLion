package com.arithfighter.not.gecko;

import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoSprite {
    private final DetectableWidget widget;
    private final int scale = 8;

    public GeckoSprite(Texture texture){
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

    public final void playCardToGecko(int mouseX, int mouseY) {
        if (isOnGecko(mouseX, mouseY)) {
            checkCardPlayed();
        }
        initCardPosition();
    }

    public void initCardPosition() {

    }

    public void checkCardPlayed() {

    }

    public boolean isOnGecko(float x, float y){
        return widget.isOnWidget(x,y);
    }
}
