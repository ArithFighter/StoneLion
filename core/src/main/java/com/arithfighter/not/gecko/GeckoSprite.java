package com.arithfighter.not.gecko;

import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoSprite {
    private final DetectableWidget defaultGecko;
    private final DetectableWidget fullGecko;
    private final int scale = 8;

    public GeckoSprite(Texture[] textures){
        defaultGecko = new SpriteWidget(textures[2], scale);
        fullGecko = new SpriteWidget(textures[6], scale);
    }

    public int getScale(){
        return scale;
    }

    public void setPosition(float x, float y){
        defaultGecko.setPosition(x,y);
        fullGecko.setPosition(x, y);
    }

    public void drawDefault(SpriteBatch batch){
        defaultGecko.draw(batch);
    }

    public void drawFull(SpriteBatch batch){
        fullGecko.draw(batch);
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
        return defaultGecko.isOnWidget(x,y)|| fullGecko.isOnWidget(x, y);
    }
}
