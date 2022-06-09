package com.arithfighter.not.entity;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.VisualEffect;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.gecko.*;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.stagecomponent.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class GamePlayComponent {
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final SumBoxController sumBoxController;
    private final VisualEffect cardFadeOut;
    private final VisualEffect cardReset;
    private boolean isCardDrag = false;
    private SpriteBatch batch;
    private boolean isReadyToResetSum = false;
    private final GeckoEntity gecko;

    public GamePlayComponent(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] spriteSheets = textureService.getTextures(textureService.getKeys()[3]);

        cardFadeOut = new VisualEffect(spriteSheets[1], 3, 3);
        cardFadeOut.setScale(16);
        cardFadeOut.setFrameDuration(0.08f);
        cardFadeOut.setDuration(0.4f);

        cardReset = new VisualEffect(spriteSheets[0], 3, 3);
        cardReset.setScale(16);
        cardReset.setFrameDuration(0.08f);
        cardReset.setDuration(0.48f);

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
                changeGeckoStateWhenPlayCard();
            }
        };

        gecko = new GeckoEntity(geckoSprite, spriteSheets);

        sumBoxController = new SumBoxController();
    }

    public SumBoxController getSumBoxController() {
        return sumBoxController;
    }

    private void changeGeckoStateWhenPlayCard() {
        if (sumBox.isCapacityWarning()) {
            gecko.setFullEating();
        } else {
            gecko.setEating();
        }
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init() {
        gecko.setNeutral();
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

        gecko.draw(batch);

        player.draw(batch);

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardFadeOut.draw(batch, AnimationPos.CENTER);

        cardReset.draw(batch, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDrag = false;
        player.activateCard(mouseX, mouseY);
        cardReset.setLastMousePoint(player.getActiveCard().getInitPoint());

        if (sumBox.isCapacityWarning()) {
            gecko.setTooFull();
        }
        if (isReadyToResetSum) {
            gecko.setSpitting();
            sumBoxController.init();
            player.setSkillStateToNeutral();
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
            gecko.touchUp(mouseX, mouseY);
            cardFadeOut.setLastMousePoint(new Point(mouseX, mouseY));
        }
        if (sumBoxController.isCapacityFull())
            isReadyToResetSum = true;
    }
}

