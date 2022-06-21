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
import com.arithfighter.not.system.RandomNumProducer;
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
    private final TransformNumber transformNumber;

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
        tabooNumber.setPoint(new Point(300, 700));
        tabooNumber.setValues();

        transformNumber = new TransformNumber(font);
        transformNumber.setPoint(new Point(200, 400));
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
        tabooNumber.setValues();
    }

    public void setNumberQuantity(int quantity) {
        numberBoxEntity.setBoxQuantity(quantity);
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

        if (sumBoxEntity.isCapacityWarning())
            stoneLion.getStoneLion().drawWarning(batch);
        else
            stoneLion.getStoneLion().drawDefault(batch);

        if (gameVariation == GameVariation.FOG)
            sumMask.update(batch);

        if (gameVariation == GameVariation.TABOO) {
            updateTabooNumber();
            tabooNumber.draw(batch);
        }
        if (gameVariation == GameVariation.TRANSFORM){
            transformNumber.draw(batch);
            if (transformNumber.getValue() == 0)
                transformNumber.setValue(numberBoxEntity);
        }

        player.getPlayer().draw(batch);

        drawCardAnimate();
    }

    private void updateTabooNumber(){
        tabooNumber.update(sumBoxEntity.getSumBoxModel().getSum());
        if (tabooNumber.isViolatingTaboos()) {
            init();
        }
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

class TransformNumber{
    private final Font font;
    private Point point;
    private int value;

    public TransformNumber(Font font){
        this.font = font;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getValue() {
        return value;
    }

    public void setValue(NumberBoxEntity numberBoxEntity) {
        RandomNumProducer rnp = new RandomNumProducer(numberBoxEntity.getMaxQuantity()-1, 0);
        value = numberBoxEntity.getNumberBoxValue(rnp.getRandomNum());
    }

    public boolean isNumberMatched(int sum){
        return value == sum;
    }

    public void draw(SpriteBatch batch){
        font.draw(batch, String.valueOf(value), point.getX(), point.getY());
    }
}