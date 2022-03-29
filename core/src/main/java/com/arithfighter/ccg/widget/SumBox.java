package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBox{
    private final Font text;
    private final SpriteWidget widget;
    private final int warningCondition = 2;
    
    public SumBox(Texture texture){
        widget = new SpriteWidget(texture,10, 36);
        text = new Font(widget.getFontSize());
    }

    public int getWarningCondition(){
        return warningCondition;
    }

    public void setPosition(float x, float y){
        widget.setPosition(x,y);
    }

    public void draw(int number, SpriteBatch batch){
        widget.drawSprite(batch);

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch){
        String sum = String.valueOf(number);

        float textX = widget.getCenterX(sum);
        float textY = widget.getCenterY();

        text.setColor(Color.WHITE);
        text.draw(batch, sum, textX, textY);
    }

    public void changeColor(int condition){
        if (condition<=warningCondition)
            widget.getSprite().setColor(Color.RED);
        else
            widget.getSprite().setColor(Color.TEAL);
    }

    public void dispose(){
        text.dispose();
    }
}
