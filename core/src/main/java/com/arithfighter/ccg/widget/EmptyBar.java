package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar{
    private final Sprite bar;
    private final Font maxSign;
    private static final int maxEnergy = 30;
    private final SpriteWidget widget;

    public EmptyBar(Texture texture){
        widget = new SpriteWidget();
        widget.configWidget(texture, 8f);
        widget.fontSize = 32;

        maxSign = new Font(widget.fontSize);

        bar = new Sprite(texture);
    }

    public float getWidth(){
        return widget.width;
    }

    public int getMax(){
        return maxEnergy;
    }

    public void setPosition(float x, float y){
        widget.posX = x;
        widget.posY = y;
    }

    private void setSprite(){
        bar.setPosition(widget.posX, widget.posY);
        bar.setSize(widget.width, widget.height);
    }

    public void draw(SpriteBatch batch, int energy) {
        setSprite();
        bar.draw(batch);
        String content = "MAX";
        if (isMax(energy)){
            maxSign.draw(
                    batch,content,
                    widget.getCenterX(content)+10,
                    widget.getCenterY()+widget.fontSize/2f
            );
        }
    }

    private boolean isMax(int energy){
        return energy >= maxEnergy;
    }

    public void dispose(){
        maxSign.dispose();
    }
}
