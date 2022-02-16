package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarGrid extends RawWidget{
    Font maxSign;
    Sprite grid;

    public BarGrid(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 8f);

        configFont(32);

        maxSign = new Font(fontSize);
        maxSign.setColor(Color.WHITE);

        grid = new Sprite(texture);
        grid.setColor(Color.SKY);
        grid.setPosition(widgetX, widgetY);
        grid.setSize(widgetWidth, widgetHeight);
    }

    public void draw(SpriteBatch batch) {
        maxSign.draw(batch, "MAX",
                widgetX+widgetWidth/2, widgetY+widgetHeight/2);
        grid.draw(batch);
    }
}
