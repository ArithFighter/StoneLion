package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox {
    Font text;
    Sprite box;
    float x, y, width, height;
    int scale = 4;
    int fontSize = 32;

    public NumberBox(Texture texture, float initX, float initY){
        text = new Font(fontSize);

        x = initX;
        y = initY;
        width = texture.getWidth()*scale;
        height = texture.getHeight()*scale;

        box = new Sprite(texture);
        box.setPosition(x,y);
        box.setSize(width, height);
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void draw(int number, SpriteBatch batch){
        box.setColor(0,0.9f,0.9f,1);
        box.draw(batch);

        String content = String.valueOf(number);
        float textX = x+width/2-content.length()*fontSize/2f;
        float textY = y+(height+fontSize)/2;
        
        text.setColor(Color.WHITE);
        text.draw(batch, content, textX, textY);
    }

    public void dispose(){
        text.dispose();
    }
}
