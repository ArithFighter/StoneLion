package com.arithfighter.not.widget;

public interface DetectableFontWidget extends VisibleWidget{
    boolean isOnWidget(float x, float y);
    float getCenterX(String content);
    float getCenterY();
    int getFontSize();
}
