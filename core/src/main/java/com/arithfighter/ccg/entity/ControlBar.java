package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.pojo.Point;
import com.arithfighter.ccg.widget.FlexibleWidget;
import com.arithfighter.ccg.widget.SpriteWidget;
import com.arithfighter.ccg.widget.Widget;
import com.arithfighter.ccg.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlBar {
    private final FlexibleWidget bar;
    private final Font font;
    private final Button leftArrow;
    private final Button rightArrow;
    private final float initWidth;
    private final int initValue;
    private final int fontSize;

    public ControlBar(Texture[] textures){
        bar = new SpriteWidget(textures[5], 0.8f);

        initValue = 10;

        initWidth = bar.getWidget().getWidth()*initValue;
        bar.updateWidth(initWidth);

        leftArrow = new Button(textures[8], 0.8f);

        rightArrow = new Button(textures[9], 0.8f);

        fontSize = 25;
        font = new Font(fontSize);
    }

    public void setPosition(int x, int y){
        bar.setPosition(x,y);
    }

    public void draw(SpriteBatch batch, String content){
        updateWidth();

        bar.draw(batch);

        Widget widget = bar.getWidget();
        Point point = widget.getPoint();
        font.draw(batch, content, point.getX()-content.length()*fontSize-30, point.getY()+fontSize/2f);

        leftArrow.setPosition(point.getX()-30, point.getY());
        leftArrow.draw(batch, "");

        rightArrow.setPosition(point.getX()+initWidth+10, point.getY());
        rightArrow.draw(batch,"");
    }

    private void updateWidth(){
        Widget widget = bar.getWidget();
        float speed = 0.1f;
        if (leftArrow.isActive() && widget.getWidth()>0){
            widget.setWidth(widget.getWidth()-initWidth/initValue*speed);
        }
        if (rightArrow.isActive() && widget.getWidth()<initWidth){
            widget.setWidth(widget.getWidth()+initWidth/initValue*speed);
        }
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
        font.dispose();
        leftArrow.dispose();
        rightArrow.dispose();
    }
}
