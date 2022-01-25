package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox {
    Font text;
    Sprite box;
    float x, y, width, height;
    float scale = 3.5f;
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

        changeNumColor(number);

        text.draw(batch, content, textX, textY);
    }

    private void changeNumColor(int number){
        int whiteNum = 15;
        int blueNum = 21;
        int yellowNum = 99;

        if (number<whiteNum){
            text.setColor(Color.WHITE);

        } else if (number<=blueNum){
            text.setColor(Color.BLUE);

        } else if (number<yellowNum){
            text.setColor(Color.YELLOW);
        }
    }

    public void dispose(){
        text.dispose();
    }
}
