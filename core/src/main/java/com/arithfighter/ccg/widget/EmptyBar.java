package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.component.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar extends RawWidget {
    Sprite bar;
    Font maxSign;
    private static final int maxEnergy = 30;

    public EmptyBar(Texture texture, float initX, float initY){
        configWidget(initX, initY, texture.getWidth(), texture.getHeight(), 8f);

        configFont(32);

        maxSign = new Font(fontSize);

        bar = new Sprite(texture);
        bar.setPosition(widgetX, widgetY);
        bar.setSize(widgetWidth, widgetHeight);
    }

    public float getWidth(){
        return widgetWidth;
    }

    public int getMax(){
        return maxEnergy;
    }

    public void draw(SpriteBatch batch, int energy) {
        bar.draw(batch);
        String content = "MAX";
        if (isMax(energy)){
            maxSign.draw(batch,content,
                    widgetX+widgetWidth/2-content.length()*fontSize/2f+10, widgetY+widgetHeight/2+fontSize);
        }
    }

    private boolean isMax(int energy){
        return energy >= maxEnergy;
    }

    public void dispose(){
        maxSign.dispose();
    }
}
