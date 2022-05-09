package com.arithfighter.not.entity;

import com.arithfighter.not.animate.AnimationPos;
import com.arithfighter.not.animate.CardAnimation;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.gecko.GeckoAnimate;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.gecko.Gecko;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.stagecomponent.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class GamePlayComponent {
    private final Gecko gecko;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final Recorder scoreRecord = new Recorder();
    private final CardAnimation cardFadeOut;
    private final CardAnimation cardReset;
    private boolean isCardDrag = false;
    private final GeckoAnimate geckoAnimate;
    private final TimeHandler geckoActionHandler = new TimeHandler();
    private SpriteBatch batch;

    public GamePlayComponent(Texture[] textures, Texture[] spriteSheets, SoundManager soundManager) {
        cardFadeOut = new CardAnimation(spriteSheets[1]);

        cardReset = new CardAnimation(spriteSheets[1]);

        Point geckoPoint = new Point(CENTER_X + GRID_X * 5,GRID_Y * 6);

        gecko = new Gecko(spriteSheets[2]) {
            @Override
            public void initCardPosition() {
                cardReset.setStart();
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
                cardFadeOut.setStart();
            }
        };
        gecko.setPosition(geckoPoint.getX(), geckoPoint.getY());

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void doWhenSumAndNumMatched() {
                scoreRecord.update(1);
                soundManager.playScoreSound();
            }
        };

        sumBox = new SumBox(textures[2]);
        Point sumPoint = new Point(CENTER_X + GRID_X * 6, GRID_Y * 11);
        sumBox.setPosition(sumPoint.getX(), sumPoint.getY());

        geckoAnimate = new GeckoAnimate(spriteSheets);
        geckoAnimate.setDrawPoint(geckoPoint);
        geckoAnimate.setScale(gecko.getScale());
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public int getScore() {
        return scoreRecord.getRecord();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init() {
        scoreRecord.reset();
        numberBoxDisplacer.init();
        player.init();
        cardFadeOut.init();
        cardReset.init();
    }

    public NumberBoxDisplacer getNumberBoxDisplacer() {
        return numberBoxDisplacer;
    }

    public void setNumberQuantity(int quantity) {
        numberBoxDisplacer.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY) {
        numberBoxDisplacer.update(player.getSum());

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw() {
        drawGecko();

        numberBoxDisplacer.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);

        drawCardAnimate();
    }

    private void drawGecko(){
        geckoActionHandler.updatePastedTime();
        geckoAnimate.setBatch(batch);

        int x = 2;

        if (geckoActionHandler.getPastedTime()>x){
            geckoAnimate.blink();
        }
        if (geckoActionHandler.getPastedTime()>x+1){
            geckoAnimate.swing();
        }
        if (geckoActionHandler.getPastedTime()>x+1.64f){
            geckoAnimate.init();
            geckoActionHandler.resetPastedTime();
        }

        if (geckoAnimate.isDefault()||geckoActionHandler.getPastedTime()<=x)
            gecko.draw(batch);
    }

    private void drawCardAnimate(){
        cardFadeOut.draw(batch, 0.4f, AnimationPos.CENTER);

        cardReset.draw(batch, 0.4f, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDrag = false;
        player.activateCard(mouseX, mouseY);
        cardReset.setLastMousePoint(player.getActiveCard().getInitPoint());
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.isCardActive())
            isCardDrag = true;
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDrag) {
            gecko.playCardToBasket(mouseX, mouseY);
            cardFadeOut.setLastMousePoint(new Point(mouseX, mouseY));
        }
    }

    public void dispose() {
        numberBoxDisplacer.dispose();
        sumBox.dispose();
    }
}