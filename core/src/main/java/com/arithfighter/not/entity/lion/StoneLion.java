package com.arithfighter.not.entity.lion;

import com.arithfighter.not.entity.DetectCardArea;
import com.arithfighter.not.widget.DetectableWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StoneLion extends DetectCardArea {
    private final DetectableWidget stoneLion;
    private final int scale = 4;

    public StoneLion(TextureRegion texture){
        stoneLion = new SpriteWidget(texture, scale);
    }

    public int getScale(){
        return scale;
    }

    public void setPosition(float x, float y){
        stoneLion.setPosition(x,y);
    }

    public void drawDefault(SpriteBatch batch){
        stoneLion.getSprite().setColor(Color.WHITE);
        stoneLion.draw(batch);
    }

    public void drawWarning(SpriteBatch batch){
        stoneLion.getSprite().setColor(Color.RED);
        stoneLion.draw(batch);
    }

    public final void playCardToLion(int mouseX, int mouseY) {
        if (isOnLion(mouseX, mouseY))
            checkCardPlayed();

        initCardPosition();
    }

    private boolean isOnLion(float x, float y) {
        return stoneLion.isOnWidget(x,y);
    }
}
