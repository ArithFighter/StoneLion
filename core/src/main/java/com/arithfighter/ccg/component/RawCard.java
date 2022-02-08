package com.arithfighter.ccg.component;

public class RawCard {
    float initX, initY;
    float cardX, cardY, cardWidth, cardHeight;

    public void setInitPosition(float initX, float initY){
        this.initX = initX;
        this.initY = initY;
    }

    public void configCard(float cardX,float cardY,float cardWidth,float cardHeight, float scale){
        this.cardX = cardX;
        this.cardY = cardY;
        this.cardWidth = cardWidth*scale;
        this.cardHeight = cardHeight*scale;
    }
}
