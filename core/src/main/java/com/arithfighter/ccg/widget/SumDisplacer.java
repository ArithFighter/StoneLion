package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumDisplacer {
    Font text;
    Sprite sumDisplayBlock;
    float blockX, blockY, blockWidth, blockHeight;
    int scale = 6;
    int fontSize = 32;
    
    public SumDisplacer(Texture texture, float initX, float initY){
        text = new Font(fontSize);

        blockX = initX;
        blockY = initY;
        blockWidth = texture.getWidth()*scale;
        blockHeight = texture.getHeight()*scale;

        sumDisplayBlock = new Sprite(texture);
        sumDisplayBlock.setColor(Color.TEAL);
        sumDisplayBlock.setPosition(blockX,blockY);
        sumDisplayBlock.setSize(blockWidth, blockHeight);
    }

    public void draw(String sum, SpriteBatch batch){
        sumDisplayBlock.draw(batch);

        float textX = blockX+blockWidth/2-sum.length()*fontSize/2f;
        float textY = blockY+(blockHeight+fontSize)/2;

        text.setColor(Color.WHITE);
        text.draw(batch, sum, textX, textY);
    }

    public void dispose(){
        text.dispose();
    }
}
