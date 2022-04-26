package com.arithfighter.not.entity;

import com.arithfighter.not.AnimationProcessor;
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
    private final CardAnimation cardFadeOut;
    private final CardAnimation cardReset;

    public GamePlayComponent(Texture[] textures, Texture[] spriteSheet, SoundManager soundManager){
        cardFadeOut = new CardAnimation(spriteSheet[1]);

        cardReset = new CardAnimation(spriteSheet[0]);

        cardPlaceBasket = new CardPlaceBasket(textures[1]) {
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
        cardFadeOut.init();
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

        cardFadeOut.draw(batch, 0.5f, AnimationPos.CENTER);

        cardReset.draw(batch, 0.5f, AnimationPos.TOP_RIGHT);
    }

    public void touchDown(int mouseX, int mouseY){
        player.activateCard(mouseX, mouseY);
        cardReset.setLastPoint(player.getActiveCard().getInitPoint());
    }

    public void touchDragged(int mouseX, int mouseY){
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY){
        cardPlaceBasket.playCardToBasket(mouseX, mouseY);
        cardFadeOut.setLastPoint(new Point(mouseX,mouseY));
    }

    public void dispose(){
        numberBoxDisplacer.dispose();
        sumBox.dispose();
    }
}

class CardAnimation{
    private final AnimationProcessor processor;
    private boolean isStart = false;
    private final TimeHandler fadeOutHandler;
    private Point lastPoint;

    public CardAnimation(Texture texture){
        processor = new AnimationProcessor(texture,3,3);
        processor.setScale(16);
        processor.setSpeed(0.08f);
        fadeOutHandler = new TimeHandler();
        lastPoint = new Point();
    }

    public void setLastPoint(Point point){
        lastPoint = point;
    }

    public void setStart(){
        isStart = true;
    }

    public void init(){
        isStart = false;
    }

    public boolean isOver(){
        return !isStart;
    }

    public void draw(SpriteBatch batch, float duration, AnimationPos pos){
        if (isStart){
            fadeOutHandler.updatePastedTime();
            if (fadeOutHandler.getPastedTime()<duration){
                processor.setPoint(lastPoint);
                processor.setBatch(batch);

                float x = 0;
                float y = 0;

                if (pos == AnimationPos.CENTER){
                    x = processor.getPoint().getX()- processor.getWidth()/2;
                    y = processor.getPoint().getY()- processor.getHeight()/2;
                }
                if(pos == AnimationPos.TOP_RIGHT){
                    x = processor.getPoint().getX();
                    y = processor.getPoint().getY();
                }
                processor.draw(x,y);
            }else{
                isStart = false;
            }
        }else {
            fadeOutHandler.resetPastedTime();
            processor.init();
        }
    }
}

enum AnimationPos{
    CENTER, TOP_RIGHT
}