package com.arithfighter.not.entity;

import com.arithfighter.not.SpriteAnimation;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.CardPlaceBasket;
import com.arithfighter.not.widget.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.WindowSetting.*;

public class GamePlayComponent {
    private final CardPlaceBasket cardPlaceBasket;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final Recorder scoreRecord = new Recorder();
    private final SpriteAnimation cardFadeOut;
    private boolean isCardPlayed = false;
    private final TimeHandler fadeOutHandler = new TimeHandler();
    private final Point lastPoint = new Point();

    public GamePlayComponent(Texture[] textures, Texture[] spriteSheet, SoundManager soundManager){
        cardFadeOut = new SpriteAnimation(spriteSheet[1], 3,3);
        cardFadeOut.setScale(16);
        cardFadeOut.setSpeed(0.08f);

        cardPlaceBasket = new CardPlaceBasket(textures[1]) {
            @Override
            public void initCardPosition() {
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
                isCardPlayed = true;
            }
        };
        cardPlaceBasket.setPosition(CENTER_X + GRID_X * 10, GRID_Y * 6);

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void doWhenSumAndNumMatched() {
                scoreRecord.update(1);
                soundManager.playScoreSound();
            }
        };

        sumBox = new SumBox(textures[2]);
        sumBox.setPosition(CENTER_X + GRID_X * 5, GRID_Y * 7);
    }

    public int getScore(){
        return scoreRecord.getRecord();
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void init(){
        scoreRecord.reset();
        numberBoxDisplacer.init();
        player.init();
        isCardPlayed = false;
    }

    public NumberBoxDisplacer getNumberBoxDisplacer(){
        return numberBoxDisplacer;
    }

    public void setNumberQuantity(int quantity){
        numberBoxDisplacer.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY){
        numberBoxDisplacer.update(player.getSum());

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw(SpriteBatch batch) {
        cardPlaceBasket.draw(batch);

        numberBoxDisplacer.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);

        if (isCardPlayed){
            fadeOutHandler.updatePastedTime();
            if (fadeOutHandler.getPastedTime()<0.5f){
                cardFadeOut.setPoint(lastPoint);
                cardFadeOut.setBatch(batch);
                cardFadeOut.draw();
            }else{
                fadeOutHandler.resetPastedTime();
                cardFadeOut.init();
                isCardPlayed = false;
            }
        }else {
            fadeOutHandler.resetPastedTime();
            cardFadeOut.init();
        }
    }

    public void touchDown(int mouseX, int mouseY){
        player.activateCard(mouseX, mouseY);
    }

    public void touchDragged(int mouseX, int mouseY){
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY){
        cardPlaceBasket.playCardToBasket(mouseX, mouseY);
        lastPoint.set(mouseX, mouseY);
    }

    public void dispose(){
        numberBoxDisplacer.dispose();
        sumBox.dispose();
    }
}