package com.arithfighter.ccg;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hand {
    TemplateCard whiteCard, greenCard;
    TemplateCard[] cards;
    float centerX = WindowSetting.CENTER_X;
    float gridX = WindowSetting.GRID_X;
    float gridY = WindowSetting.GRID_Y;

    public void initHand(AssetManager assetManager){
        whiteCard = new TemplateCard(centerX+gridX*4,gridY*1,gridX*3, Color.WHITE, assetManager);
        greenCard = new TemplateCard(centerX+gridX*8,gridY*1,gridX*3,Color.GREEN, assetManager);

        cards = new TemplateCard[]{whiteCard, greenCard};
    }

    public void draw(SpriteBatch batch, float x , float y){
        for (TemplateCard card: cards)
            card.draw(batch, x, y);
    }

    public void checkActive(float x, float y){
        for (TemplateCard card: cards)
                card.checkActive(x, y);
    }

    public void updateWhenDrag(float x , float y){
        for (TemplateCard card: cards)
                card.updateWhenDrag(x, y);
    }

    public void resetHand(){
        for (TemplateCard card: cards)
                card.resetCard();
    }

    public boolean isCardActive(){
        int condition = 0;
        for (int i = 0; i< cards.length;i++)
            if (cards[i].isActive())
                condition = i;

        return cards[condition].isActive();
    }
}
