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

    public void drawGecko(SpriteBatch batch) {
        geckoActionHandler.updatePastedTime();
        this.batch = batch;

        int initTime = 2;

        switchState(initTime);
    }

    public void init() {
        geckoAnimate.init();
    }

    private void switchState(float initTime) {
        switch (geckoState) {
            case NEUTRAL:
                drawGeckoAction(initTime);

                if (geckoAnimate.isDefault() || geckoActionHandler.getPastedTime() <= initTime)
                    geckoSprite.draw(batch);
                break;
            case EATING:
                geckoAnimate.getEat().draw(batch);

                if (geckoAnimate.getEat().isEnd()) {
                    geckoSprite.draw(batch);
                    geckoState = GeckoState.NEUTRAL;
                }
                break;
        }
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
}
