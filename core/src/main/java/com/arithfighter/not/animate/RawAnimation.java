package com.arithfighter.not.animate;

import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;

public class RawAnimation extends AnimationComponent{
    public RawAnimation(Texture spriteSheet, int cols, int rows) {
        setProcessor(new AnimationProcessor(spriteSheet, cols, rows));

        setTimeHandler(new TimeHandler());
    }

    public void handleAnimation() {
        getTimeHandler().updatePastedTime();

        getProcessor().setPoint(getDrawPoint());
        getProcessor().draw(getDrawPoint().getX(), getDrawPoint().getY());
    }

    public void resetTimeAndAnimation() {
        getTimeHandler().resetPastedTime();
        getProcessor().init();
    }
}
