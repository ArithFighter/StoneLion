package com.arithfighter.ccg.card;

import com.arithfighter.ccg.widget.Point;
import com.badlogic.gdx.graphics.Texture;

public class SpriteCard extends RawCard{
    public SpriteCard(float initX, float initY){
        setInitPoint(new Point(initX,initY));
        setPoint(new Point(initX,initY));
    }

    public void setSize(Texture texture, float scale){
        configSize(texture.getWidth(), texture.getHeight(), scale);
    }

    private void configSize(float width, float height, float scale){
        setWidth(scale*width);
        setHeight(scale*height);
    }
}
