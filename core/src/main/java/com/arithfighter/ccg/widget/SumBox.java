package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBox{
    private final Font text;
    private final Sprite sumDisplayBlock;
    private final TextureWidget widget;
    private final WidgetSetting setting;
    
    public SumBox(Texture texture, float initX, float initY){
        widget = new TextureWidget();
        widget.configWidget(texture, 10);
        widget.posX = initX;
        widget.posY = initY;
        widget.fontSize = 36;

        setting = new WidgetSetting(widget.fontSize);
        
        text = new Font(widget.fontSize);

        sumDisplayBlock = new Sprite(texture);
        sumDisplayBlock.setColor(Color.TEAL);
        sumDisplayBlock.setPosition(widget.posX, widget.posY);
        sumDisplayBlock.setSize(widget.width, widget.height);
    }

    public void draw(int number, int condition, SpriteBatch batch){
        changeColor(condition);

        sumDisplayBlock.draw(batch);

        drawText(number, batch);
    }

    private void drawText(int number, SpriteBatch batch){
        String sum = String.valueOf(number);

        float textX = setting.getCenterX(widget.posX, widget.width, sum);
        float textY = setting.getCenterY(widget.posY, widget.height);

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
