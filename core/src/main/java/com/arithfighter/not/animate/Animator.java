package com.arithfighter.not.animate;

import com.badlogic.gdx.utils.TimeUtils;

public class Animator {
    public final void animateFlashy(float positionX, float positionY, int ratePerSec) {
        long timeGap = 1000 / ratePerSec;
        if (TimeUtils.millis() % (timeGap + timeGap) < timeGap) {
            renderEffect(positionX, positionY);
        }
    }

    public final void animateFlashy(int ratePerSec) {
        long timeGap = 1000 / ratePerSec;
        if (TimeUtils.millis() % (timeGap + timeGap) < timeGap) {
            renderEffect();
        }
    }

    public final void animateShaking(float positionX, float positionY, int ratePerSec) {
        long timeGap = 1000/ratePerSec;
        float randomNum = 0;

        if (TimeUtils.millis() % (timeGap + timeGap) < timeGap){
            randomNum = generateRandomNumber();
        }
        renderEffect(positionX + randomNum, positionY + randomNum);
    }

    private float generateRandomNumber() {
        int max = 10;
        float min = 0.5f;

        return (float) (Math.random() * (max - min) + min + 1);
    }

    public void renderEffect(float x, float y) {
    }

    public void renderEffect() {
    }
}
