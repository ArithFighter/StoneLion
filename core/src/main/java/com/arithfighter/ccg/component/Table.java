package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Table extends RawWidget{
    Sprite table;

    public Table(Texture texture, int x, int y){
        configWidget(x,y,texture.getWidth(), texture.getHeight(), 14);

        table = new Sprite(texture);
        table.setPosition(widgetX,widgetY);
        table.setSize(widgetWidth, widgetHeight);
    }
    
    public void draw(SpriteBatch batch){
        table.draw(batch);
    }
    
    public boolean isOnTable(float x, float y){
        return x > widgetX && x < widgetX + widgetWidth &&
                y > widgetY && y < widgetY + widgetHeight;
    }
}
