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
    private final WidgetAttribute attribute;
    private final Point point;
    
    public SumBox(Texture texture){
        widget = new SpriteWidget();
        widget.configWidget(texture, 10);
        widget.fontSize = 36;
        widget.point = new Point(0,0);
        point = widget.point;

        attribute = new WidgetAttribute(widget.fontSize);
        
        text = new Font(widget.fontSize);

        sumDisplayBlock = new Sprite(texture);
    }

    public void setPosition(float x, float y){
        point.setX(x);
        point.setY(y);
    }

    private void setSprite(){
        sumDisplayBlock.setColor(Color.TEAL);
        sumDisplayBlock.setPosition(point.getX(), point.getY());
        sumDisplayBlock.setSize(widget.width, widget.height);
    }

    public void draw(int number, int condition, SpriteBatch batch){
        setSprite();

        changeColor(condition);

        sumDisplayBlock.draw(batch);

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch){
        String sum = String.valueOf(number);

        float textX = attribute.getCenterX(point.getX(), widget.width, sum);
        float textY = attribute.getCenterY(point.getY(), widget.height);

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
