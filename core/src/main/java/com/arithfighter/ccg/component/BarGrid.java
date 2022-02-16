package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid extends RawWidget{
    Font maxSign;
    Sprite grid;

    public BarGrid(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 0.8f);

        configFont(32);

        maxSign = new Font(fontSize);
        maxSign.setColor(Color.WHITE);

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
        grid.setPosition(widgetX, widgetY);
        grid.setSize(widgetWidth, widgetHeight);
    }

    public float getWidth(){
        return widgetWidth;
    }

    public void setPosX(float x){
        grid.setPosition(x,widgetY);
    }

    public void draw(SpriteBatch batch, float width) {
        grid.setSize(width,widgetHeight);
        grid.draw(batch);
        maxSign.draw(batch, "MAX",
                widgetX+width/2, widgetY+widgetHeight);
    }

    public void dispose(){
        maxSign.dispose();
    }
}
