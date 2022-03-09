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
    private final WidgetAttribute attribute;

    public EmptyBar(Texture texture, float initX, float initY){
        widget = new SpriteWidget();
        widget.configWidget(texture, 8f);
        widget.posX = initX;
        widget.posY = initY;
        widget.fontSize = 32;

        maxSign = new Font(widget.fontSize);

        attribute = new WidgetAttribute(widget.fontSize);

        bar = new Sprite(texture);
        bar.setPosition(widget.posX, widget.posY);
        bar.setSize(widget.width, widget.height);
    }

    public float getWidth(){
        return widget.width;
    }

    public int getMax(){
        return maxEnergy;
    }

    public void draw(SpriteBatch batch, int energy) {
        bar.draw(batch);
        String content = "MAX";
        if (isMax(energy)){
            maxSign.draw(batch,content,
                    attribute.getCenterX(widget.posX, widget.width, content)+10,
                    attribute.getCenterY(widget.posY, widget.height)+widget.fontSize/2f);
        }
    }

    private boolean isMax(int energy){
        return energy >= maxEnergy;
    }

    public void dispose(){
        maxSign.dispose();
    }
}
