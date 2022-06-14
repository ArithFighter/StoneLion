package com.arithfighter.not.gecko;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class GeckoEntity {
    private final GeckoController geckoController;

    public GeckoEntity(GeckoSprite geckoSprite, Texture[] spriteSheets) {
        Point geckoPoint = new Point(CENTER_X + GRID_X * 5, GRID_Y * 6);

        geckoSprite.setPosition(geckoPoint.getX(), geckoPoint.getY());

        GeckoAnimationService geckoAnimationService = new GeckoAnimationService(spriteSheets);

        GeckoAnimate geckoAnimate = new GeckoAnimate(geckoAnimationService.getCharacterAnimatable());
        geckoAnimate.setDrawPoint(geckoPoint);
        geckoAnimate.setScale(geckoSprite.getScale());

        geckoController = new GeckoController();
        geckoController.setGeckoSprite(geckoSprite);
        geckoController.setGeckoAnimate(geckoAnimate);
    }

    public void setEating() {
        geckoController.init();
        geckoController.setGeckoState(GeckoState.EATING);
    }

    public void setFullEating() {
        geckoController.init();
        geckoController.setGeckoState(GeckoState.FULL_EATING);
    }

    public void setNeutral() {
        geckoController.init();
        geckoController.setGeckoState(GeckoState.NEUTRAL);
    }

    public void setTooFull() {
        if (geckoController.isNotFullEating())
            geckoController.setGeckoState(GeckoState.TOO_FULL);
    }

    public void setSpitting() {
        geckoController.init();
        geckoController.setGeckoState(GeckoState.SPIT);
    }

    public void touchUp(int x, int y) {
        geckoController.touchUp(x,y);
    }

    public void draw(SpriteBatch batch) {
        geckoController.setBatch(batch);

        geckoController.drawGecko();
    }
}
