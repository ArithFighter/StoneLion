package com.arithfighter.not.entity.game;

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
    private final VariationController variationController;

    public GamePlayComponent(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] cards = textureService.getTextures(textureService.getKeys()[1]);
        Texture[] spriteSheets = textureService.getTextures(textureService.getKeys()[3]);

        createCardAnimate(spriteSheets);

        numberBoxEntity = new NumberBoxEntity(textures, font) {
            @Override
            public void doWhenSumAndNumMatched() {
                soundManager.playScoreSound();
                variationController.revealSumMask();
            }
        };

        sumBoxEntity = new SumBoxEntity(textures[2], font);

        player = new PlayerService(cards, sumBoxEntity.getSumBoxModel(), CharacterList.KNIGHT);

        stoneLion = new StoneLionEntity(spriteSheets[2], player.getPlayer(), cardAnimate);

        variationController = new VariationController(textures[5], font){
            @Override
            public void doWhenViolatingTaboos() {
                init();
            }
        };
        variationController.setNumberBoxEntity(numberBoxEntity);
        variationController.setSumMaskPoint(sumBoxEntity.getPoint());
        variationController.setTabooNumberPoint(new Point(300, 700));
        variationController.setTransformNumberPoint(new Point(50, 700));
    }

    private void createCardAnimate(Texture[] spriteSheets) {
        CardAnimationService cas = new CardAnimationService(spriteSheets);

        cardAnimate = new CardAnimate(cas.getVisualEffects());
        for (SpecialEffect ve : cardAnimate.getVisualEffects())
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
        variationController.init();
    }

    public void update(int mouseX, int mouseY) {
        int sum = sumBoxEntity.getSumBoxModel().getSum();
        numberBoxEntity.update(sum);

        player.getPlayer().updateWhenTouchCard(mouseX, mouseY);

        if (numberBoxEntity.isAllNumZero())
            init();
    }

    public void draw(GameVariation gameVariation) {
        numberBoxEntity.draw(batch);

        sumBoxEntity.draw(batch);

        numberBoxEntity.setBoxQuantity(6);

        if (sumBoxEntity.isCapacityWarning())
            stoneLion.getStoneLion().drawWarning(batch);
        else
            stoneLion.getStoneLion().drawDefault(batch);

        variationController.setSum(sumBoxEntity.getSumBoxModel().getSum());
        variationController.changeGameVariation(gameVariation, batch);

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

class VariationController{
    private final SumMask sumMask;
    private final TabooNumber tabooNumber;
    private final TransformNumber transformNumber;
    private NumberBoxEntity numberBoxEntity;
    private int sum;

    public VariationController(Texture texture, Font font){
        sumMask = new SumMask(texture);

        tabooNumber = new TabooNumber(font);

        tabooNumber.setValues();

        transformNumber = new TransformNumber(font);
    }

    public void init(){
        tabooNumber.setValues();
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setNumberBoxEntity(NumberBoxEntity numberBoxEntity) {
        this.numberBoxEntity = numberBoxEntity;
    }

    public void setSumMaskPoint(Point point){
        sumMask.getSumMask().setPosition(point.getX(), point.getY());
    }

    public void setTabooNumberPoint(Point point){
        tabooNumber.setPoint(point);
    }

    public void setTransformNumberPoint(Point point){
        transformNumber.setPoint(point);
    }

    public void revealSumMask(){
        sumMask.init();
        sumMask.setReveal();
    }

    public void changeGameVariation(GameVariation gameVariation, SpriteBatch batch){
        switch (gameVariation) {
            case FOG:
                sumMask.update(batch);
                break;
            case TABOO:
                updateTabooNumber();
                tabooNumber.draw(batch);
                break;
            case TRANSFORM:
                transformNumber.draw(batch);
                updateTransformNumber();
                break;
        }
    }

    private void updateTransformNumber() {
        transformNumber.setValue(numberBoxEntity);
        if (transformNumber.isNumberMatched(sum)) {
            transformNumber.transform(numberBoxEntity);
            transformNumber.init();
        }
    }

    private void updateTabooNumber() {
        tabooNumber.update(sum);
        if (tabooNumber.isViolatingTaboos()) {
            doWhenViolatingTaboos();
        }
    }

    public void doWhenViolatingTaboos() {

    }
}