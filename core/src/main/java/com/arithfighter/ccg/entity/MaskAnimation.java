package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.time.TimeHandler;
import com.arithfighter.ccg.widget.Mask;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MaskAnimation {
    private final TimeHandler timeHandler;
    private final Mask[] masks;

    public MaskAnimation(Mask[] masks){
        timeHandler = new TimeHandler();

        this.masks = masks;
    }

    public void draw(SpriteBatch batch){
        float drawSpeed = 0.2f;
        timeHandler.updatePastedTime();

        for (int i = 0; i< masks.length;i++){
            if (timeHandler.getPastedTime()-drawSpeed<drawSpeed*i)
                masks[i].draw(batch);
        }
    }

    public void debug(SpriteBatch batch){
        float drawSpeed = 0.2f;
        timeHandler.updatePastedTime();

        for (int i = 0; i< masks.length;i++){
            if (timeHandler.getPastedTime()-drawSpeed<drawSpeed*i)
                masks[i].debug(batch);
        }
    }

    public void init(){
        timeHandler.resetPastedTime();
    }
}
