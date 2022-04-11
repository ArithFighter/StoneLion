package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.entity.player.Player;
import com.arithfighter.ccg.pojo.Recorder;
import com.arithfighter.ccg.widget.CardPlaceBasket;
import com.arithfighter.ccg.widget.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class GamePlayComponent {
    private final CardPlaceBasket cardPlaceBasket;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final Recorder scoreRecord = new Recorder();

    public GamePlayComponent(Texture[] textures, SoundManager soundManager){
        cardPlaceBasket = new CardPlaceBasket(textures[1]) {
            @Override
            public void initCardPosition() {
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
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
    }

    public NumberBoxDisplacer getNumberBoxDisplacer(){
        return numberBoxDisplacer;
    }

    public void update(int mouseX, int mouseY){
        numberBoxDisplacer.update(player.getSum());

        numberBoxDisplacer.setBoxQuantity(99);

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw(SpriteBatch batch) {
        cardPlaceBasket.draw(batch);

        numberBoxDisplacer.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);
    }

    public void touchDown(int mouseX, int mouseY){
        player.activateCard(mouseX, mouseY);
    }

    public void touchDragged(int mouseX, int mouseY){
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY){
        cardPlaceBasket.playCardToBasket(mouseX, mouseY);
    }

    public void dispose(){
        numberBoxDisplacer.dispose();
        sumBox.dispose();
    }
}