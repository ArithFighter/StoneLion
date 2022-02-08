package com.arithfighter.ccg.component;

public class RawWidget {
    float widgetX, widgetY, widgetWidth, widgetHeight;
    int fontSize;

    public void configWidget(float x, float y, float width, float height, float scale){
        widgetX = x;
        widgetY = y;
        widgetWidth = scale*width;
        widgetHeight = scale*height;
    }

    public void configFont(int size){
        fontSize = size;
    }
}
