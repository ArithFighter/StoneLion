package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardPlaceBasket {
    private final DetectableWidget widget;

    public CardPlaceBasket(Texture texture){
        widget = new SpriteWidget(texture, 3);

        widget.getSprite().setColor(Color.BROWN);
    }

    public void setPosition(int x, int y){
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
