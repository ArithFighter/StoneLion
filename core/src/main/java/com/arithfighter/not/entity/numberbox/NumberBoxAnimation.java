package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.animate.VisualEffect;
import com.arithfighter.not.time.Timer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class NumberBoxAnimation {
    private final VisualEffect visualEffect;
    private final Timer timer;
    private SpriteBatch batch;
    private int matchedBoxIndex = -1;

    public NumberBoxAnimation(NumberBox[] numberBoxes) {
        visualEffect = new VisualEffect() {
            @Override
            public void renderEffect() {
                numberBoxes[matchedBoxIndex].draw(batch, 0);
            }
        };
        timer = new Timer();
        timer.setTime(1.2f);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setMatchedBoxIndex(int i) {
        matchedBoxIndex = i;
    }

    public void draw() {
        int ratePerSec = 8;

        if (matchedBoxIndex >= 0) {
            timer.update();

            visualEffect.animateFlashy(ratePerSec);

            if (timer.isTimesOut())
                init();
        }
    }

    public void init() {
        timer.init();
        matchedBoxIndex -= matchedBoxIndex + 1;
    }
}
