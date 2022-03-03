package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar extends Widget {
    Sprite bar;
    Font maxSign;
    private static final int maxEnergy = 30;

    public EmptyBar(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 8f);

        configFont(32);

        maxSign = new Font(fontSize);

        bar = new Sprite(texture);
        bar.setPosition(posX, posY);
        bar.setSize(width, height);
    }

    public float getWidth(){
        return width;
    }

    public int getMax(){
        return maxEnergy;
    }

    public void draw(SpriteBatch batch, int energy) {
        bar.draw(batch);
        String content = "MAX";
        if (isMax(energy)){
            maxSign.draw(batch,content,
                    posX + width /2-content.length()*fontSize/2f+10, posY + height /2+fontSize);
        }
    }

    private boolean isMax(int energy){
        return energy >= maxEnergy;
    }

    public void dispose(){
        maxSign.dispose();
    }
}
