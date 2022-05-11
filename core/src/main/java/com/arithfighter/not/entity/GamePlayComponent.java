package com.arithfighter.not.entity;

import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.CardAnimation;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.gecko.GeckoAnimate;
import com.arithfighter.not.gecko.GeckoController;
import com.arithfighter.not.gecko.GeckoState;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.gecko.GeckoSprite;
import com.arithfighter.not.widget.stagecomponent.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class GamePlayComponent {
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final Recorder scoreRecord = new Recorder();
    private final CardAnimation cardFadeOut;
    private final CardAnimation cardReset;
    private boolean isCardDrag = false;
    private final GeckoController geckoController;
    private SpriteBatch batch;

    public GamePlayComponent(Texture[] textures, Texture[] spriteSheets, SoundManager soundManager) {
        cardFadeOut = new CardAnimation(spriteSheets[1]);

        cardReset = new CardAnimation(spriteSheets[1]);

        sumBox = new SumBox(textures[2]);
        Point sumPoint = new Point(CENTER_X + GRID_X * 6, GRID_Y * 11);
        sumBox.setPosition(sumPoint.getX(), sumPoint.getY());

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void doWhenSumAndNumMatched() {
                scoreRecord.update(1);
                soundManager.playScoreSound();
            }
        };

        Point geckoPoint = new Point(CENTER_X + GRID_X * 5, GRID_Y * 6);

        GeckoSprite geckoSprite = new GeckoSprite(spriteSheets) {
            @Override
            public void initCardPosition() {
                cardReset.setStart();
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
                cardFadeOut.setStart();
                if (sumBox.isCapacityWarning()) {
                    geckoController.init();
                    geckoController.setGeckoState(GeckoState.FULL_EATING);
                } else {
                    geckoController.init();
                    geckoController.setGeckoState(GeckoState.EATING);
                }
            }
        };
        geckoSprite.setPosition(geckoPoint.getX(), geckoPoint.getY());

        GeckoAnimate geckoAnimate = new GeckoAnimate(spriteSheets);
        geckoAnimate.setDrawPoint(geckoPoint);
        geckoAnimate.setScale(geckoSprite.getScale());

        geckoController = new GeckoController();
        geckoController.setGeckoSprite(geckoSprite);
        geckoController.setGeckoAnimate(geckoAnimate);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public int getScore() {
        return scoreRecord.getRecord();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init() {
        scoreRecord.reset();
        numberBoxDisplacer.init();
        player.init();
        cardFadeOut.init();
        cardReset.init();
        geckoController.init();
        geckoController.setGeckoState(GeckoState.NEUTRAL);
    }

    public NumberBoxDisplacer getNumberBoxDisplacer() {
        return numberBoxDisplacer;
    }

    public void setNumberQuantity(int quantity) {
        numberBoxDisplacer.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY) {
        numberBoxDisplacer.update(player.getSum());

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw() {
        numberBoxDisplacer.draw(batch);

        sumBox.setCapacity(player.getCardCapacity());
        sumBox.draw(player.getSum(), batch);

        geckoController.setBatch(batch);

        geckoController.drawGecko();

        player.draw(batch);

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardFadeOut.draw(batch, 0.4f, AnimationPos.CENTER);

        cardReset.draw(batch, 0.4f, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDrag = false;
        player.activateCard(mouseX, mouseY);
        cardReset.setLastMousePoint(player.getActiveCard().getInitPoint());

        if (sumBox.isCapacityWarning())
            geckoController.setGeckoState(GeckoState.TOO_FULL);

        if (player.isCapacityManagerEmpty())
            if (geckoController.isNotEating())
                geckoController.setGeckoState(GeckoState.NEUTRAL);
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.isCardActive())
            isCardDrag = true;
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDrag) {
            geckoController.getGeckoSprite().playCardToGecko(mouseX, mouseY);
            cardFadeOut.setLastMousePoint(new Point(mouseX, mouseY));
        }
    }

    public void dispose() {
        numberBoxDisplacer.dispose();
        sumBox.dispose();
    }
}