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
    private final SumMask sumMask;
    private final TabooNumber tabooNumber;

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

        tabooNumber = new TabooNumber(font);
        tabooNumber.setPoint(new Point(300,700));
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

        tabooNumber.update(sum);
    }

    private void initNumbersAndSum(){
        numberBoxEntity.init();
        sumBoxEntity.init();
    }

    public void draw(GameVariation gameVariation) {
        numberBoxEntity.draw(batch);

        sumBoxEntity.draw(batch);

        if (sumBoxEntity.isCapacityWarning())
            stoneLion.getStoneLion().drawWarning(batch);
        else
            stoneLion.getStoneLion().drawDefault(batch);

        if (gameVariation == GameVariation.FOG)
            sumMask.update(batch);

        tabooNumber.draw(batch);

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

class TabooNumber{
    private final Font font;
    private int value1 = 0;
    private int value2 = 0;
    private Point point;

    public TabooNumber(Font font){
        this.font = font;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        font.draw(batch, value1+" "+value2, point.getX(), point.getY());
    }

    public void update(int sum){
        if (sum == value1)
            value1 = 0;
        if (sum == value2)
            value2 = 0;
    }

    public boolean isViolatingTaboos(){
        return value1 == 0&& value2 == 0;
    }
}