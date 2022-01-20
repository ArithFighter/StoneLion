package com.arithfighter.ccg.widget;

public class SpriteAttribution {
    float x;
    float y;
    float width;
    float height;

    public SpriteAttribution(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void updateX(float movement){
        x+=movement;
    }

    public void updateY(float movement){
        y+=movement;
    }
}
