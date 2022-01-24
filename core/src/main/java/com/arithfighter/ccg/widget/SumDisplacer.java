package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumDisplacer {
    Font text;
    Sprite sumDisplayBlock;
    float x, y, width, height;
    int scale = 6;
    int fontSize = 32;
    
    public SumDisplacer(Texture texture, float initX, float initY){
        text = new Font(fontSize);

        x = initX;
        y = initY;
        width = texture.getWidth()*scale;
        height = texture.getHeight()*scale;

        sumDisplayBlock = new Sprite(texture);
        sumDisplayBlock.setColor(Color.GOLDENROD);
        sumDisplayBlock.setPosition(x,y);
        sumDisplayBlock.setSize(width, height);
    }

    public void draw(String sum, SpriteBatch batch){
        sumDisplayBlock.draw(batch);

        float textX = x+width/2-sum.length()*fontSize/2f;
        float textY = y+(height+fontSize)/2;

        text.setColor(Color.WHITE);
        text.draw(batch, sum, textX, textY);
    }

    public void dispose(){
        text.dispose();
    }
}
