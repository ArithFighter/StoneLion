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
        switch (geckoState) {
            case NEUTRAL:
                handleNeutral();
                break;
            case EATING:
                handleEating();
                break;
        }
    }

    public void init(){
        geckoAnimate.init();
        geckoActionHandler.resetPastedTime();
    }

    private void handleNeutral(){
        int initTime = 2;
        float pastedTime = geckoActionHandler.getPastedTime();

        geckoActionHandler.updatePastedTime();

        if (pastedTime > initTime)
            geckoAnimate.getBlink().draw(batch);
        if (pastedTime > initTime + 1)
            geckoAnimate.getSwing().draw(batch);
        if (pastedTime > initTime + 1.64f && geckoAnimate.isDefault())
            init();
        if (geckoAnimate.isDefault())
            geckoSprite.draw(batch);
    }

    private void handleEating(){
        geckoAnimate.getEat().draw(batch);

        if (geckoAnimate.getEat().isEnd()) {
            init();
            geckoState = GeckoState.NEUTRAL;
            geckoSprite.draw(batch);
        }
    }
}
