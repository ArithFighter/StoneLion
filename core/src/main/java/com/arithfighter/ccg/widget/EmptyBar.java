package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmptyBar{
    private final Sprite bar;
    private final Font maxSign;
    private static final int maxEnergy = 30;
    private final TextureWidget widget;
    private final WidgetSetting setting;

    public EmptyBar(Texture texture, float initX, float initY){
        widget = new TextureWidget();
        widget.configWidget(texture, 8f);
        widget.posX = initX;
        widget.posY = initY;
        widget.fontSize = 32;

        maxSign = new Font(widget.fontSize);

        setting = new WidgetSetting(widget.fontSize);

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
                    setting.getCenterX(widget.posX, widget.width, content)+10,
                    setting.getCenterY(widget.posY, widget.height));
        }
    }

    private boolean isMax(int energy){
        return energy >= maxEnergy;
    }

    public void dispose(){
        maxSign.dispose();
    }
}
