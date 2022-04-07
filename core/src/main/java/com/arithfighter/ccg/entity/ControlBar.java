package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.pojo.Point;
import com.arithfighter.ccg.widget.SpriteWidget;
import com.arithfighter.ccg.widget.VisibleWidget;
import com.arithfighter.ccg.widget.Widget;
import com.arithfighter.ccg.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlBar {
    private final VisibleWidget[] grids;
    private final Font font;
    private final ButtonProducer arrows;
    private final int maxValue = 6;
    private int value;
    private final int fontSize;
    private boolean isButtonLock = false;

    public ControlBar(Texture[] textures){
        grids = new VisibleWidget[maxValue];

        for (int i = 0; i< grids.length;i++)
            grids[i] = new SpriteWidget(textures[5], 0.5f);

        arrows = new ButtonProducer(textures);

        value = maxValue;

        fontSize = 25;
        font = new Font(fontSize);
    }

    public int getValue(){
        return value;
    }

    public void setPosition(int x, int y){
        for (int i = 0; i< grids.length;i++){
            grids[i].setPosition(x+grids[i].getWidget().getWidth()*i+10*i, y);
        }
    }

    public void draw(SpriteBatch batch, String content){
        for (int i =0;i<value;i++)
            grids[i].draw(batch);

        addFont(batch, content);

        Widget widget = grids[0].getWidget();
        Point point = widget.getPoint();

        arrows.drawLeftArrow(batch, point.getX()-50, point.getY());
        arrows.drawRightArrow(batch, point.getX(), point.getY(), (widget.getWidth()+10) * grids.length);
    }

    private void addFont(SpriteBatch batch, String content){
        Widget widget = grids[0].getWidget();
        Point point = widget.getPoint();
        font.draw(batch, content, point.getX()-content.length()*fontSize-50, point.getY()+fontSize/2f);
    }

    public void update(){
        value = Math.max(0,value);

        if (!isButtonLock){
            if (arrows.isLeftActive() && value>=0)
                value-=1;
            if (arrows.isRightActive() && value< maxValue)
                value+=1;
        }
        isButtonLock = arrows.isLeftActive() || arrows.isRightActive();
    }

    public void activate(float x, float y){
        arrows.activate(x, y);
    }

    public void deactivate(){
        arrows.deactivate();
    }

    public void dispose(){
        font.dispose();
        arrows.dispose();
    }
}

class ButtonProducer{
    private final Button leftArrow;
    private final Button rightArrow;

    public ButtonProducer(Texture[] textures){
        leftArrow = new Button(textures[8], 0.8f);

        rightArrow = new Button(textures[9], 0.8f);
    }

    public void drawLeftArrow(SpriteBatch batch, float x, float y){
        leftArrow.setPosition(x-50, y);
        leftArrow.draw(batch, "");
    }

    public void drawRightArrow(SpriteBatch batch, float x, float y, float barWidth){
        rightArrow.setPosition(x+barWidth, y);
        rightArrow.draw(batch,"");
    }

    public boolean isLeftActive(){
        return leftArrow.isActive();
    }

    public boolean isRightActive(){
        return rightArrow.isActive();
    }

    public void activate(float x, float y){
        leftArrow.activate(x,y);
        rightArrow.activate(x,y);
    }

    public void deactivate(){
        leftArrow.deactivate();
        rightArrow.deactivate();
    }

    public void dispose(){
        leftArrow.dispose();
        rightArrow.dispose();
    }
}
