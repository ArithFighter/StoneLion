package com.arithfighter.not.pojo;

public class Shape {
    private float width, height;

    public Shape(){

    }

    public Shape(float width, float height){
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
