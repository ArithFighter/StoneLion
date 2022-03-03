package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid extends Widget {
    Sprite grid;

    public BarGrid(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 0.8f);

        configFont(32);

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
        grid.setPosition(posX, posY);
        grid.setSize(width, height);
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void setPos(float x, float y){
        this.posX = x;
        this.posY = y;
        grid.setPosition(x,y);
    }

    public void draw(SpriteBatch batch, float width) {
        this.width = width;
        grid.setSize(this.width, height);
        grid.draw(batch);
    }

    public void dispose(){

    }
}
