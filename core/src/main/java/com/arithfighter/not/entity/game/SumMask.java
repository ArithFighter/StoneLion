package com.arithfighter.not.entity.game;

import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.time.Timer;
import com.arithfighter.not.widget.a1.Mask;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class SumMask {
    private final Mask sumMask;
    private final Timer timer;
    private boolean isReveal = false;

    public SumMask(Texture texture) {
        sumMask = new Mask(texture, 5);
        timer = new Timer();
        timer.setTime(1.2f);
    }

    public Mask getSumMask() {
        return sumMask;
    }

    public void init() {
        isReveal = false;
        timer.init();
    }

    public void setReveal() {
        isReveal = true;
    }

    public void update(SpriteBatch batch) {
        if (isReveal) {
            timer.update();

            if (timer.isTimesOut())
                init();
        } else
            sumMask.draw(batch);
    }
}
