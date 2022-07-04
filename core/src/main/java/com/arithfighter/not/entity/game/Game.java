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
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.entity.player.PlayerService;
import com.arithfighter.not.entity.sumbox.SumBoxEntity;
import com.arithfighter.not.file.FileLibrary;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
    private final NumberBoxEntity numberBoxEntity;
    private Player player;
    private CardAnimate cardAnimate;
    private SpriteBatch batch;
    private boolean isCardDragging = false;
    private final SumBoxEntity sumBoxEntity;
    private final StoneLionEntity stoneLion;
    private final VariationController variationController;
    private final PlayerService playerService;

    public Game(TextureService textureService, SoundManager soundManager, Font font) {
        Texture[] widgets = textureService.getTextureMap().get(textureService.getKeys()[0]);
        Texture[] cards = textureService.getTextureMap().get(textureService.getKeys()[1]);
        Texture[] spriteSheets = textureService.getTextureMap().get(textureService.getKeys()[2]);

        createCardAnimate(spriteSheets);

        numberBoxEntity = new NumberBoxEntity(widgets, font) {
            @Override
            public void doWhenSumAndNumMatched() {
                soundManager.playScoreSound();
                variationController.revealSumMask();
            }
        };
        sumBoxEntity = new SumBoxEntity(widgets[2], font);

        stoneLion = new StoneLionEntity(spriteSheets[8], cardAnimate);

        variationController = new VariationController(widgets[5], font, sumBoxEntity){
            @Override
            public void doWhenViolatingTaboos() {
                init();
            }
        };
        variationController.setNumberBoxEntity(numberBoxEntity);

        playerService = new PlayerService(cards);
        playerService.setSumBoxModel(sumBoxEntity.getSumBoxModel());
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public void setCharacter(CharacterList character) {
        int index = 0;

        for (int i = 0;i<CharacterList.values().length;i++){
            if (CharacterList.values()[i] == character)
                index =i;
        }

        this.player = playerService.getPlayers()[index];

        stoneLion.setPlayer(player);
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
        cardAnimate.init();
        variationController.init();
    }

    public void update(int mouseX, int mouseY) {
        int sum = sumBoxEntity.getSumBoxModel().getSum();
        numberBoxEntity.update(sum);

        if (cardAnimate.isAllNotStart())
            player.playOnCardAnimation(mouseX, mouseY);
    }

    public boolean isAllNumZero(){
        return numberBoxEntity.isAllNumZero();
    }

    public void draw(GameVariation gameVariation, int boxQuantity) {
        numberBoxEntity.draw(batch);

        sumBoxEntity.draw(batch);

        numberBoxEntity.setBoxQuantity(boxQuantity);

        if (sumBoxEntity.isCapacityWarning())
            stoneLion.getStoneLion().drawWarning(batch);
        else
            stoneLion.getStoneLion().drawDefault(batch);

        variationController.setSum(sumBoxEntity.getSumBoxModel().getSum());
        variationController.changeGameVariation(gameVariation, batch);

        player.draw(batch);

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardAnimate.getCardFadeOut().draw(batch, AnimationPos.CENTER);
        cardAnimate.getCardReset().draw(batch, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDragging = false;

        //you can only touch cards when all card animation finished
        if (cardAnimate.isAllNotStart()){
            player.activateCard(mouseX, mouseY);
            cardAnimate.getCardReset().setLastMousePoint(player.getActiveCard().getInitPoint());
        }
        sumBoxEntity.touchDown();
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.isCardActive())
            isCardDragging = true;

        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDragging) {
            stoneLion.getStoneLion().playCardToLion(mouseX, mouseY);
            cardAnimate.getCardFadeOut().setLastMousePoint(new Point(mouseX, mouseY));
        }
        sumBoxEntity.touchUp();
    }
}