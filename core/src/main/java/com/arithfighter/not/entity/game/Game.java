package com.arithfighter.not.entity.game;

import com.arithfighter.not.entity.CandleStick;
import com.arithfighter.not.file.texture.TextureGetter;
import com.arithfighter.not.file.texture.TextureService;
import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.se.SpecialEffect;
import com.arithfighter.not.file.audio.SoundManager;
import com.arithfighter.not.card.CardAnimationEntity;
import com.arithfighter.not.card.CardAnimationService;
import com.arithfighter.not.entity.numberbox.NumberBoxEntity;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.entity.player.PlayerService;
import com.arithfighter.not.entity.sumbox.SumBoxEntity;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;

import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
    private final NumberBoxEntity numberBoxEntity;
    private Player player;
    private CardAnimationEntity cardAnimate;
    private SpriteBatch batch;
    private boolean isCardDragging = false;
    private final SumBoxEntity sumBoxEntity;
    private final VariationController variationController;
    private final PlayerService playerService;
    private final CandleStick candleStick;
    private final VisibleWidget cardHighlight;
    private boolean isReadyToPlayCard = false;

    public Game(TextureService textureService, SoundManager soundManager, Font font) {
        TextureGetter tg = new TextureGetter(textureService);

        Texture[] animationSheets = {
                tg.getAnimationMap().get("animation/card-fade-in.png"),
                tg.getAnimationMap().get("animation/card-fade-out.png")
        };
        createCardAnimate(animationSheets);

        Texture[] numberTextures = {
                tg.getObjectMap().get("object/bell.png"),
                tg.getGuiMap().get("gui/Golden_Square.png")
        };
        numberBoxEntity = new NumberBoxEntity(numberTextures, font) {
            @Override
            public void doWhenSumAndNumMatched() {
                soundManager.playScoreSound();
                variationController.revealSumMask();
            }
        };
        sumBoxEntity = new SumBoxEntity(tg.getObjectMap().get("object/ghost-fire.png"), font);

        variationController = new VariationController(tg.getGuiMap().get("gui/white-block.png"), font, sumBoxEntity) {
            @Override
            public void doWhenViolatingTaboos() {
                init();
            }
        };
        variationController.setNumberBoxEntity(numberBoxEntity);

        playerService = new PlayerService(textureService.getTextureMap().get(textureService.getKeys()[1]));
        playerService.setSumBoxModel(sumBoxEntity.getSumBoxModel());

        Texture[] candleT = {
                tg.getObjectMap().get("object/red-candle.png"),
                tg.getObjectMap().get("object/candle-fire.png"),
                tg.getObjectMap().get("object/Hand-CandleStick.png"),
                tg.getObjectMap().get("object/candle-head.png"),
                tg.getObjectMap().get("object/candle-bottom.png")
        };
        candleStick = new CandleStick(candleT){
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
        candleStick.setPoint(new Point(100,0));

        cardHighlight = new SpriteWidget(tg.getGuiMap().get("gui/card-outLine.png"), 2f);
    }

    public CandleStick getCandleStick() {
        return candleStick;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public void setCharacter(CharacterList character) {
        int index = 0;

        for (int i = 0; i < CharacterList.values().length; i++) {
            if (CharacterList.values()[i] == character)
                index = i;
        }

        this.player = playerService.getPlayers()[index];
    }

    private void createCardAnimate(Texture[] spriteSheets) {
        CardAnimationService cas = new CardAnimationService(spriteSheets);

        cardAnimate = new CardAnimationEntity(cas.getSpecialEffects());
        for (SpecialEffect ve : cardAnimate.getSpecialEffects())
            ve.setScale(18);
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

    public boolean isAllNumZero() {
        return numberBoxEntity.isAllNumZero();
    }

    public void draw(GameVariation gameVariation, int boxQuantity) {
        numberBoxEntity.draw(batch);

        sumBoxEntity.draw(batch);

        numberBoxEntity.setBoxQuantity(boxQuantity);

        variationController.setSum(sumBoxEntity.getSumBoxModel().getSum());
        variationController.changeGameVariation(gameVariation, batch);

        candleStick.draw(batch);

        player.draw(batch);

        if (isReadyToPlayCard) {
            Point point = player.getActiveCard().getPoint();
            cardHighlight.setPosition(point.getX(), point.getY());
            cardHighlight.draw(batch);
        }

        drawCardAnimate();
    }

    private void drawCardAnimate() {
        cardAnimate.getCardFadeOut().draw(batch, AnimationPos.CENTER);
        cardAnimate.getCardReset().draw(batch, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDragging = false;

        //you can only touch cards when all card animation finished
        if (cardAnimate.isAllNotStart()) {
            player.activateCard(mouseX, mouseY);
            cardAnimate.getCardReset().setLastMousePoint(player.getActiveCard().getInitPoint());
        }
        sumBoxEntity.touchDown();
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.isCardActive()){
            isCardDragging = true;
            player.updateWhenDrag(mouseX, mouseY);
            isReadyToPlayCard = candleStick.isOnCandle(mouseX, mouseY);
        }
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDragging) {
            candleStick.playCardOnCandle(mouseX, mouseY);
            cardAnimate.getCardFadeOut().setLastMousePoint(new Point(mouseX, mouseY));
            isReadyToPlayCard = false;
        }
        sumBoxEntity.touchUp();
    }
}