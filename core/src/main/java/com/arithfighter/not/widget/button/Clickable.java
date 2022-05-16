package com.arithfighter.not.widget.button;

public interface Clickable {
    void on(float x, float y);
    boolean isOn();
    void off();
}
