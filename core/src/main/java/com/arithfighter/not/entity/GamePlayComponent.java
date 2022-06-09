package com.arithfighter.not.entity;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.CardAnimation;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.gecko.GeckoAnimate;
import com.arithfighter.not.gecko.GeckoController;
import com.arithfighter.not.gecko.GeckoState;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.gecko.GeckoSprite;
import com.arithfighter.not.widget.stagecomponent.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class GamePlayComponent {
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private  final SumBoxController sumBoxController;
    private final CardAnimation cardFadeOut;
    private final CardAnimation cardReset;
    private boolean isCardDrag = false;
    private final GeckoController geckoController;
    private SpriteBatch batch;
    private boolean isReadyToResetSum = false;

    public GamePlayComponent(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] spriteSheets = textureService.getTextures(textureService.getKeys()[3]);

        cardFadeOut = new CardAnimation(spriteSheets[1]);

        cardReset = new CardAnimation(spriteSheets[1]);

        sumBox = new SumBox(textures[2]);
        Point sumPoint = new Point(CENTER_X + GRID_X * 6, GRID_Y * 11);
        sumBox.setPosition(sumPoint.getX(), sumPoint.getY());
        sumBox.setFont(font);

        numberBoxDisplacer = new NumberBoxDisplacer(textures, font) {
            @Override
            public void doWhenSumAndNumMatched() {
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
                if (geckoController.isNotFullEating() && geckoController.isNotSpitting()) {
                    player.playCard();
                    cardFadeOut.setStart();
                    changeGeckoStateWhenPlayCard();
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

        sumBoxController = new SumBoxController();
    }

    public SumBoxController getSumBoxController() {
        return sumBoxController;
    }

    private void changeGeckoStateWhenPlayCard() {
        if (sumBox.isCapacityWarning()) {
            geckoController.init();
            geckoController.setGeckoState(GeckoState.FULL_EATING);
        } else {
            geckoController.init();
            geckoController.setGeckoState(GeckoState.EATING);
        }
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init() {
        geckoController.init();
        geckoController.setGeckoState(GeckoState.NEUTRAL);
        sumBox.init();
        numberBoxDisplacer.init();
        sumBoxController.init();
        player.init();
        cardFadeOut.init();
        cardReset.init();
        isReadyToResetSum = false;
    }

    public NumberBoxDisplacer getNumberBoxDisplacer() {
        return numberBoxDisplacer;
    }

    public void setNumberQuantity(int quantity) {
        numberBoxDisplacer.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY) {
        numberBoxDisplacer.update(sumBoxController.getSum());

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw() {
        numberBoxDisplacer.draw(batch);

        sumBox.setCapacity(sumBoxController.getCardCapacity());
        sumBox.draw(sumBoxController.getSum(), batch);

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

        if (sumBox.isCapacityWarning()){
            if (geckoController.isNotFullEating())
                geckoController.setGeckoState(GeckoState.TOO_FULL);
        }
        if (isReadyToResetSum){
            geckoController.init();
            sumBoxController.init();
            player.setSkillStateToNeutral();
            geckoController.setGeckoState(GeckoState.SPIT);
            isReadyToResetSum = false;
        }
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
        if (sumBoxController.isCapacityFull())
            isReadyToResetSum = true;
    }
}