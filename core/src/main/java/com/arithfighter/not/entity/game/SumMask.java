package com.arithfighter.not.entity.game;

import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.a1.Mask;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class SumMask {
    private final Mask sumMask;
    private final TimeHandler timeHandler;
    private boolean isReveal = false;

    public SumMask(Texture texture) {
        sumMask = new Mask(texture, 5);
        timeHandler = new TimeHandler();
    }

    public Mask getSumMask() {
        return sumMask;
    }

    public void init() {
        isReveal = false;
        timeHandler.resetPastedTime();
    }

    public void setReveal() {
        isReveal = true;
    }

    public void update(SpriteBatch batch) {
        if (isReveal) {
            timeHandler.updatePastedTime();
            if (timeHandler.getPastedTime() >= 1.2f)
                init();
        } else
            sumMask.draw(batch);
    }
}
