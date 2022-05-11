package com.arithfighter.not.gecko;

import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoController {
    private GeckoSprite geckoSprite;
    private GeckoAnimate geckoAnimate;
    private final TimeHandler geckoNeutralHandler = new TimeHandler();
    private final TimeHandler geckoEatingHandler = new TimeHandler();
    private GeckoState geckoState = GeckoState.NEUTRAL;
    private SpriteBatch batch;

    public void setGeckoSprite(GeckoSprite geckoSprite) {
        this.geckoSprite = geckoSprite;
    }

    public void setGeckoAnimate(GeckoAnimate geckoAnimate) {
        this.geckoAnimate = geckoAnimate;
    }

    public GeckoSprite getGeckoSprite() {
        return geckoSprite;
    }

    public void setGeckoState(GeckoState geckoState) {
        this.geckoState = geckoState;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public boolean isNotSpitting(){
        return geckoState!=GeckoState.SPIT;
    }

    public void drawGecko() {
        switch (geckoState) {
            case NEUTRAL:
                handleNeutral();
                break;
            case EATING:
                handleEating();
                break;
            case TOO_FULL:
                geckoSprite.drawFull(batch);
                break;
            case FULL_EATING:
                geckoAnimate.eatWhenFull(batch);
                if (geckoAnimate.isAllActionEnd()){
                    geckoState = GeckoState.TOO_FULL;
                    geckoSprite.drawFull(batch);
                    geckoAnimate.init();
                }
                break;
            case SPIT:
                geckoAnimate.spit(batch);
                if (geckoAnimate.isAllActionEnd()){
                    geckoState = GeckoState.NEUTRAL;
                    geckoSprite.drawFull(batch);
                }
                break;
        }
    }

    public void init() {
        geckoAnimate.init();
        geckoNeutralHandler.resetPastedTime();
        geckoEatingHandler.resetPastedTime();
    }

    private void handleNeutral() {
        int initTime = 2;
        float pastedTime = geckoNeutralHandler.getPastedTime();

        geckoNeutralHandler.updatePastedTime();

        if (pastedTime > initTime)
            geckoAnimate.blink(batch);
        if (pastedTime > initTime + 1)
            geckoAnimate.swing(batch);
        if (pastedTime > initTime + 1.64f && geckoAnimate.isAllActionEnd())
            init();
        if (geckoAnimate.isAllActionEnd())
            geckoSprite.drawDefault(batch);
    }

    private void handleEating() {
        float pastedTime = geckoEatingHandler.getPastedTime();

        geckoEatingHandler.updatePastedTime();

        geckoAnimate.eat(batch);

        if (pastedTime > 1.2) {
            geckoAnimate.lick(batch);
        }
        if (geckoAnimate.isAllActionEnd() && pastedTime > 2) {
            init();
            geckoState = GeckoState.NEUTRAL;
            geckoSprite.drawDefault(batch);
        }
    }
}
