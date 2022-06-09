package com.arithfighter.not.entity;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.VisualEffect;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.card.CardAnimate;
import com.arithfighter.not.card.CardAnimationService;
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
    private final SumBoxModel sumBoxModel;
    private CardAnimate cardAnimate;
    private boolean isCardDrag = false;
    private SpriteBatch batch;
    private boolean isReadyToResetSum = false;
    private GeckoEntity gecko;

    public GamePlayComponent(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] spriteSheets = textureService.getTextures(textureService.getKeys()[3]);

        createCardAnimate(spriteSheets);

        numberBoxDisplacer = new NumberBoxDisplacer(textures, font) {
            @Override
            public void doWhenSumAndNumMatched() {
                soundManager.playScoreSound();
            }
        };

        createGecko(spriteSheets);

        sumBox = new SumBox(textures[2]);
        Point sumPoint = new Point(CENTER_X + GRID_X * 6, GRID_Y * 11);
        sumBox.setPosition(sumPoint.getX(), sumPoint.getY());
        sumBox.setFont(font);

        sumBoxModel = new SumBoxModel();
    }

    private void createCardAnimate(Texture[] spriteSheets){
        CardAnimationService cas = new CardAnimationService(spriteSheets);

        cardAnimate = new CardAnimate(cas.getVisualEffects());
        for (VisualEffect ve: cardAnimate.getVisualEffects())
            ve.setScale(16);
    }

    public void createGecko(Texture[] spriteSheets){
        GeckoSprite geckoSprite = new GeckoSprite(spriteSheets) {
            @Override
            public void initCardPosition() {
                cardAnimate.getCardReset().setStart();
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
                cardAnimate.getCardFadeOut().setStart();
                changeGeckoStateWhenPlayCard();
            }
        };

        gecko = new GeckoEntity(geckoSprite, spriteSheets);
    }

    public SumBoxModel getSumBoxModel() {
        return sumBoxModel;
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
        sumBoxModel.init();
        player.init();
        cardAnimate.getCardFadeOut().init();
        cardAnimate.getCardReset().init();
        isReadyToResetSum = false;
    }

    public NumberBoxDisplacer getNumberBoxDisplacer() {
        return numberBoxDisplacer;
    }

    public void setNumberQuantity(int quantity) {
        numberBoxDisplacer.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY) {
        numberBoxDisplacer.update(sumBoxModel.getSum());

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw() {
        numberBoxDisplacer.draw(batch);

        sumBox.setCapacity(sumBoxModel.getCardCapacity());
        sumBox.draw(sumBoxModel.getSum(), batch);

        gecko.draw(batch);

        player.draw(batch);

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardAnimate.getCardFadeOut().draw(batch, AnimationPos.CENTER);
        cardAnimate.getCardReset().draw(batch, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDrag = false;
        player.activateCard(mouseX, mouseY);
        cardAnimate.getCardReset().setLastMousePoint(player.getActiveCard().getInitPoint());

        if (sumBox.isCapacityWarning()) {
            gecko.setTooFull();
        }
        if (isReadyToResetSum) {
            gecko.setSpitting();
            sumBoxModel.init();
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
            cardAnimate.getCardFadeOut().setLastMousePoint(new Point(mouseX, mouseY));
        }
        if (sumBoxModel.isCapacityFull())
            isReadyToResetSum = true;
    }
}

