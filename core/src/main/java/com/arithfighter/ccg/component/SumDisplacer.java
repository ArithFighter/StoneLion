package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumDisplacer extends RawWidget{
    Font text;
    Sprite sumDisplayBlock;
    
    public SumDisplacer(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 10);
        
        configFont(36);
        
        text = new Font(fontSize);

        sumDisplayBlock = new Sprite(texture);
        sumDisplayBlock.setColor(Color.GOLDENROD);
        sumDisplayBlock.setPosition(widgetX,widgetY);
        sumDisplayBlock.setSize(widgetWidth, widgetHeight);
    }

    public void draw(int number, SpriteBatch batch){
        sumDisplayBlock.draw(batch);

        String sum = String.valueOf(number);
        float textX = widgetX+widgetWidth/2-sum.length()*fontSize/2f;
        float textY = widgetY+(widgetHeight+fontSize)/2;

        text.setColor(Color.WHITE);
        text.draw(batch, sum, textX, textY);
    }

    public void dispose(){
        text.dispose();
    }
}
