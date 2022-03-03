package com.arithfighter.ccg.widget;

public class WidgetSetting {
    int fontSize;

    public WidgetSetting(int fontSize) {
        this.fontSize = fontSize;
    }

    public float getCenterX(float x, float width, String content) {
        float midLength = width / 2 - content.length() * fontSize / 2f;

        return x + midLength;
    }

    public float getCenterY(float y, float height) {
        float midHeight = (height + fontSize) / 2;

        return y + midHeight;
    }
}
