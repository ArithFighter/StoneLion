package com.arithfighter.not.entity;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.se.SpecialEffect;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.card.CardAnimate;
import com.arithfighter.not.card.CardAnimationService;
import com.arithfighter.not.entity.lion.StoneLionEntity;
import com.arithfighter.not.entity.numberbox.NumberBoxEntity;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.entity.player.PlayerService;
import com.arithfighter.not.entity.sumbox.SumBoxEntity;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.a1.Mask;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlayComponent {
    private final NumberBoxEntity numberBoxEntity;
    private final PlayerService player;
    private CardAnimate cardAnimate;
    private SpriteBatch batch;
    private boolean isReadyToResetSum = false;
    private boolean isCardDragging = false;
    private final SumBoxEntity sumBoxEntity;
    private final StoneLionEntity stoneLion;
    private final SumMask sumMask;

    public GamePlayComponent(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] cards = textureService.getTextures(textureService.getKeys()[1]);
        Texture[] spriteSheets = textureService.getTextures(textureService.getKeys()[3]);

        createCardAnimate(spriteSheets);

        numberBoxEntity = new NumberBoxEntity(textures, font) {
            @Override
            public void doWhenSumAndNumMatched() {
                soundManager.playScoreSound();
                sumMask.init();
                sumMask.setReveal();
            }
        };

        sumBoxEntity = new SumBoxEntity(textures[2], font);

        player = new PlayerService(cards, sumBoxEntity.getSumBoxModel(), CharacterList.KNIGHT);

        stoneLion = new StoneLionEntity(spriteSheets[2], player.getPlayer(), cardAnimate);

        sumMask = new SumMask(textures);

        Point point = sumBoxEntity.getPoint();
        sumMask.getSumMask().setPosition(point.getX(), point.getY());
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

        player.getPlayer().updateWhenTouchCard(mouseX, mouseY);

        if (numberBoxEntity.isAllNumZero())
            initNumbersAndSum();
    }

    private void initNumbersAndSum(){
        numberBoxEntity.init();
        sumBoxEntity.init();
    }

    public void draw() {
        numberBoxEntity.draw(batch);

        sumBoxEntity.draw(batch);

        if (sumBoxEntity.isCapacityWarning())
            stoneLion.getStoneLion().drawWarning(batch);
        else
            stoneLion.getStoneLion().drawDefault(batch);

        sumMask.update(batch);

        player.getPlayer().draw(batch);

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardAnimate.getCardFadeOut().draw(batch, AnimationPos.CENTER);
        cardAnimate.getCardReset().draw(batch, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDragging = false;
        player.getPlayer().activateCard(mouseX, mouseY);
        cardAnimate.getCardReset().setLastMousePoint(player.getPlayer().getActiveCard().getInitPoint());

        if (isReadyToResetSum) {
            sumBoxEntity.init();
            isReadyToResetSum = false;
        }
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.getPlayer().isCardActive())
            isCardDragging = true;
        player.getPlayer().updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDragging) {
            stoneLion.getStoneLion().playCardToLion(mouseX, mouseY);
            cardAnimate.getCardFadeOut().setLastMousePoint(new Point(mouseX, mouseY));
        }
        if (sumBoxEntity.isCapacityFull())
            isReadyToResetSum = true;
    }
}

class SumMask{
    private final Mask sumMask;
    private final TimeHandler timeHandler;
    private boolean isReveal = false;

    public SumMask(Texture[] textures){
        sumMask = new Mask(textures[5], 5);
        timeHandler = new TimeHandler();
    }

    public Mask getSumMask() {
        return sumMask;
    }

    public void init(){
        isReveal = false;
        timeHandler.resetPastedTime();
    }

    public void setReveal(){
        isReveal = true;
    }

    public void update(SpriteBatch batch){
        if (isReveal){
            timeHandler.updatePastedTime();
            if (timeHandler.getPastedTime()>=1.5f)
                init();
        }else
            sumMask.draw(batch);
    }
}