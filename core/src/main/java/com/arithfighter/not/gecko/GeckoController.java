package com.arithfighter.not.gecko;

import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeckoController {
    private GeckoSprite geckoSprite;
    private GeckoAnimate geckoAnimate;
    private final TimeHandler geckoActionHandler = new TimeHandler();
    private GeckoState geckoState = GeckoState.NEUTRAL;

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
        geckoAnimate.setBatch(batch);

        int initTime = 2;

        if (geckoState == GeckoState.NEUTRAL)
            drawGeckoAction(initTime);

        if (geckoAnimate.isDefault() || geckoActionHandler.getPastedTime() <= initTime)
            geckoSprite.draw(batch);
    }

    private void drawGeckoAction(float initTime) {
        if (geckoActionHandler.getPastedTime() > initTime) {
            geckoAnimate.blink();
        }
        if (geckoActionHandler.getPastedTime() > initTime + 1) {
            geckoAnimate.swing();
        }
        if (geckoActionHandler.getPastedTime() > initTime + 1.64f) {
            geckoAnimate.init();
            geckoActionHandler.resetPastedTime();
        }
    }
}
