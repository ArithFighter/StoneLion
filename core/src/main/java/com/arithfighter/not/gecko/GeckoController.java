package com.arithfighter.not.gecko;

import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoController {
    private GeckoSprite geckoSprite;
    private GeckoAnimate geckoAnimate;
    private final TimeHandler geckoActionHandler = new TimeHandler();
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

    public void drawGecko() {
        geckoActionHandler.updatePastedTime();

        switch (geckoState) {
            case NEUTRAL:
                handleNeutral();
                break;
            case EATING:
                handleEating();
                break;
        }
    }

    public void init() {
        geckoAnimate.init();
    }

    private void handleNeutral(){
        int initTime = 2;

        drawGeckoAction(initTime);

        if (geckoAnimate.isDefault()||geckoActionHandler.getPastedTime() < initTime)
            geckoSprite.draw(batch);
    }

    private void drawGeckoAction(float initTime) {
        if (geckoActionHandler.getPastedTime() > initTime) {
            geckoAnimate.getBlink().draw(batch);
        }
        if (geckoActionHandler.getPastedTime() > initTime + 1) {
            geckoAnimate.getSwing().draw(batch);
        }
        if (geckoActionHandler.getPastedTime() > initTime + 1.64f) {
            geckoAnimate.init();
            geckoActionHandler.resetPastedTime();
        }
    }

    private void handleEating(){
        geckoAnimate.getEat().draw(batch);

        if (geckoAnimate.getEat().isEnd()) {
            geckoState = GeckoState.NEUTRAL;
            geckoSprite.draw(batch);
        }
    }
}
