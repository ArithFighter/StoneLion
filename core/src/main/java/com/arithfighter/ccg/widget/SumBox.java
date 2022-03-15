package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBox{
    private final Font text;
    private final Sprite sumDisplayBlock;
    private final SpriteWidget widget;
    private final Point point;
    
    public SumBox(Texture texture){
        widget = new SpriteWidget(36);
        widget.setSize(texture, 10);
        point = widget.getPoint();
        
        text = new Font(widget.getFontSize());

        sumDisplayBlock = new Sprite(texture);
    }

    public void setPosition(float x, float y){
        point.set(x,y);
    }

    private void setSprite(){
        sumDisplayBlock.setColor(Color.TEAL);
        sumDisplayBlock.setPosition(point.getX(), point.getY());
        sumDisplayBlock.setSize(widget.getWidth(), widget.getHeight());
    }

    public void draw(int number, int condition, SpriteBatch batch){
        setSprite();

        changeColor(condition);

        sumDisplayBlock.draw(batch);

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch){
        String sum = String.valueOf(number);

        float textX = widget.getCenterX(sum);
        float textY = widget.getCenterY();

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
