package com.arithfighter.not.entity;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.se.SpecialEffect;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.card.CardAnimate;
import com.arithfighter.not.card.CardAnimationService;
import com.arithfighter.not.entity.lion.StoneLion;
import com.arithfighter.not.entity.numberbox.NumberBoxEntity;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.entity.sumbox.SumBoxEntity;
import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlayComponent {
    private final NumberBoxEntity numberBoxEntity;
    private final Player player;
    private CardAnimate cardAnimate;
    private boolean isCardDragging = false;
    private SpriteBatch batch;
    private boolean isReadyToResetSum = false;
    private final SumBoxEntity sumBoxEntity;
    private final StoneLion stoneLion;

    public GamePlayComponent(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] cards = textureService.getTextures(textureService.getKeys()[1]);
        Texture[] spriteSheets = textureService.getTextures(textureService.getKeys()[3]);

        createCardAnimate(spriteSheets);

        numberBoxEntity = new NumberBoxEntity(textures, font) {
            @Override
            public void doWhenSumAndNumMatched() {
                soundManager.playScoreSound();
            }
        };

        sumBoxEntity = new SumBoxEntity(textures[2], font);

        player = new Player(cards, CharacterList.KNIGHT){
            final SumBoxModel sumBoxModel = sumBoxEntity.getSumBoxModel();
            @Override
            public void checkNumberCardPlayed() {
                sumBoxModel.update(getHand().getCardNumber());
            }

            @Override
            public void doWhenResettingCardPlay() {
                sumBoxModel.init();
                sumBoxModel.update(getHand().getCardNumber());
            }
        };

        stoneLion = new StoneLion(spriteSheets){
            @Override
            public void initCardPosition() {
                cardAnimate.getCardReset().setStart();
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                cardAnimate.getCardFadeOut().setStart();
                player.playCard();
            }
        };
        stoneLion.setPosition(900,200);
    }

    private void createCardAnimate(Texture[] spriteSheets){
        CardAnimationService cas = new CardAnimationService(spriteSheets);

        cardAnimate = new CardAnimate(cas.getVisualEffects());
        for (SpecialEffect ve: cardAnimate.getVisualEffects())
            ve.setScale(16);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void init() {
        sumBoxEntity.init();
        numberBoxEntity.init();
        cardAnimate.getCardFadeOut().init();
        cardAnimate.getCardReset().init();
        isReadyToResetSum = false;
    }

    public void setNumberQuantity(int quantity) {
        numberBoxEntity.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY) {
        int sum = sumBoxEntity.getSumBoxModel().getSum();
        numberBoxEntity.update(sum);

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw() {
        numberBoxEntity.draw(batch);

        sumBoxEntity.draw(batch);

        stoneLion.drawDefault(batch);

        player.draw(batch);

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardAnimate.getCardFadeOut().draw(batch, AnimationPos.CENTER);
        cardAnimate.getCardReset().draw(batch, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDragging = false;
        player.activateCard(mouseX, mouseY);
        cardAnimate.getCardReset().setLastMousePoint(player.getActiveCard().getInitPoint());

        if (sumBoxEntity.isCapacityWarning()) {
            stoneLion.drawWarning(batch);
        }
        if (isReadyToResetSum) {
            sumBoxEntity.init();
            isReadyToResetSum = false;
        }
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.isCardActive())
            isCardDragging = true;
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDragging) {
            cardAnimate.getCardFadeOut().setLastMousePoint(new Point(mouseX, mouseY));
        }
        if (sumBoxEntity.isCapacityFull())
            isReadyToResetSum = true;
    }
}