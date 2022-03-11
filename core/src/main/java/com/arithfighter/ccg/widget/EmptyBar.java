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
    private final Point point;

    public EmptyBar(Texture texture){
        widget = new SpriteWidget(23);
        widget.setSize(texture, 8f);
        point = widget.point;

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
        point.setX(x);
        point.setY(y);
    }

    private void setSprite(){
        bar.setPosition(point.getX(), point.getY());
        bar.setSize(widget.width, widget.height);
    }

    public void draw(SpriteBatch batch, int energy) {
        setSprite();
        bar.draw(batch);
        drawMaxSign(energy, batch);
    }

    private void drawMaxSign(int energy, SpriteBatch batch){
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
