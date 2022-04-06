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
    private final Button leftArrow;
    private final Button rightArrow;
    private final int maxValue = 8;
    private int value;
    private final int fontSize;

    public ControlBar(Texture[] textures){
        grids = new VisibleWidget[maxValue];
        for (int i = 0; i< grids.length;i++){
            grids[i] = new SpriteWidget(textures[5], 0.5f);
        }
        value = maxValue;

        leftArrow = new Button(textures[8], 0.8f);

        rightArrow = new Button(textures[9], 0.8f);

        fontSize = 25;
        font = new Font(fontSize);
    }

    public void setPosition(int x, int y){
        for (int i = 0; i< grids.length;i++){
            grids[i].setPosition(x+grids[i].getWidget().getWidth()*i+10*i, y);
        }
    }

    public void draw(SpriteBatch batch, String content){
        for (int i =0;i<value;i++)
            grids[i].draw(batch);

        Widget widget = grids[0].getWidget();
        Point point = widget.getPoint();
        font.draw(batch, content, point.getX()-content.length()*fontSize-50, point.getY()+fontSize/2f);

        leftArrow.setPosition(point.getX()-50, point.getY());
        leftArrow.draw(batch, "");

        rightArrow.setPosition(point.getX()+(widget.getWidth()+10) * grids.length, point.getY());
        rightArrow.draw(batch,"");
    }

    public void update(){
        if (value<=0)
            value = 0;
        if (leftArrow.isActive() && value>=0)
            value-=1;
        if (rightArrow.isActive() && value< maxValue)
            value+=1;
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
