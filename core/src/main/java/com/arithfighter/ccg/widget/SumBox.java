package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBox extends RawWidget {
    Font text;
    Sprite sumDisplayBlock;
    
    public SumBox(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 10);
        
        configFont(36);
        
        text = new Font(fontSize);

        sumDisplayBlock = new Sprite(texture);
        sumDisplayBlock.setColor(Color.TEAL);
        sumDisplayBlock.setPosition(widgetX,widgetY);
        sumDisplayBlock.setSize(widgetWidth, widgetHeight);
    }

    public void draw(int number, int condition, SpriteBatch batch){
        changeColor(condition);

        sumDisplayBlock.draw(batch);

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch){
        String sum = String.valueOf(number);
        float textX = widgetX+widgetWidth/2-sum.length()*fontSize/2f;
        float textY = widgetY+(widgetHeight+fontSize)/2;

        text.setColor(Color.WHITE);
        text.draw(batch, sum, textX, textY);
    }

    private void changeColor(int condition){
        if (condition<3)
            sumDisplayBlock.setColor(Color.RED);
        else
            sumDisplayBlock.setColor(Color.TEAL);
    }

    public void dispose(){
        text.dispose();
    }
}
