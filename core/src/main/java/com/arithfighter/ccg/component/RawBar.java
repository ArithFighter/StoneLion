package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RawBar extends RawWidget{
    Sprite bar;
    Font maxSign;

    public RawBar(Texture texture, float initX, float initY){
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

    public void draw(SpriteBatch batch, boolean isMax) {
        bar.draw(batch);
        String content = "MAX";
        if (isMax){
            maxSign.draw(batch,content,
                    widgetX+widgetWidth/2-content.length()*fontSize/2f+10, widgetY+widgetHeight/2+fontSize);
        }
    }

    public void dispose(){
        maxSign.dispose();
    }
}
