package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.SoundManager;
import com.arithfighter.ccg.entity.player.Player;
import com.arithfighter.ccg.widget.CardPlaceBasket;
import com.arithfighter.ccg.widget.button.Button;
import com.arithfighter.ccg.widget.SumBox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class GameComponent {
    private final CardPlaceBasket cardPlaceBasket;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final Button returnButton;
    private boolean returnToMenuFlag = false;

    public GameComponent(Texture[] textures, GameDataAccessor dataAccessor, SoundManager soundManager){
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

        numberBoxDisplacer = new NumberBoxDisplacer(textures[3]) {
            @Override
            public void doWhenSumAndNumMatched() {
                dataAccessor.updateScore(1);
                soundManager.playScoreSound();
            }
        };

        sumBox = new SumBox(textures[2]);
        sumBox.setPosition(CENTER_X + GRID_X * 5, GRID_Y * 7);

        returnButton = new Button(textures[6], 1.8f);
        returnButton.setPosition(1000, 600);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void init(){
        returnToMenuFlag = false;
        numberBoxDisplacer.refresh();
        player.init();
    }

    public NumberBoxDisplacer getNumberBoxDisplacer(){
        return numberBoxDisplacer;
    }

    public Player getPlayer() {
        return player;
    }

    public CardPlaceBasket getBoardArea(){
        return cardPlaceBasket;
    }

    public Button getReturnButton(){
        return returnButton;
    }

    public boolean isReturnToMenu(){
        return returnToMenuFlag;
    }

    public void update(int mouseX, int mouseY){
        numberBoxDisplacer.update(player.getSum());
        numberBoxDisplacer.setBoxQuantity(99);

        player.updateWhenTouchCard(mouseX, mouseY);

        returnToMenuFlag = returnButton.isActive();

        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            refresh();
        }
    }

    private void refresh(){
        numberBoxDisplacer.refresh();
    }

    public void draw(SpriteBatch batch) {
        returnButton.draw(batch, "Return");

        cardPlaceBasket.draw(batch);

        numberBoxDisplacer.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);
    }

    public void dispose(){
        numberBoxDisplacer.dispose();
        sumBox.dispose();
        returnButton.dispose();
    }
}
