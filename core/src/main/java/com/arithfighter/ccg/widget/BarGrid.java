package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.widget.RawWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid extends RawWidget {
    Sprite grid;

    public BarGrid(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 0.8f);

        configFont(32);

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
        grid.setPosition(widgetX, widgetY);
        grid.setSize(widgetWidth, widgetHeight);
    }

    public float getWidth(){
        return widgetWidth;
    }

    public float getHeight(){
        return widgetHeight;
    }

    public void setPos(float x, float y){
        widgetX = x;
        widgetY = y;
        grid.setPosition(x,y);
    }

    public void draw(SpriteBatch batch, float width) {
        widgetWidth = width;
        grid.setSize(widgetWidth,widgetHeight);
        grid.draw(batch);
    }

    public void dispose(){

    }
}
