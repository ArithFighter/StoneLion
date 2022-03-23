package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.*;
import com.arithfighter.ccg.widget.BoardArea;
import com.arithfighter.ccg.widget.Button;
import com.arithfighter.ccg.widget.SumBox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class Game {
    private BoardArea boardArea;
    private Player player;
    private final SumBox sumBox;
    private final Button returnButton;
    private boolean returnToMenuFlag = false;

    public Game(Texture[] textures, Texture[] cards, CharacterList character){
        createPlayer(textures, cards, character);

        createBoardArea(textures);

        sumBox = new SumBox(textures[2]);
        sumBox.setPosition(CENTER_X + GRID_X * 8, GRID_Y * 7);

        returnButton = new Button(textures[6]);
        returnButton.setPosition(1000, 600);
    }

    private void createPlayer(Texture[] textures,Texture[] cards, CharacterList character){
        player = new Player(textures, cards, character) {
            @Override
            public void doWhenCardPlayed() {
            }

            @Override
            public void castSkill(CharacterList character) {

            }
        };
    }

    private void createBoardArea(Texture[] textures){
        boardArea = new BoardArea(textures[1]) {
            @Override
            public void initCardPosition() {
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
            }
        };
        boardArea.setPosition(CENTER_X + GRID_X * 4, GRID_Y * 6);
    }

    public void init(){
        returnToMenuFlag = false;
        player.init();
    }

    public Player getPlayer() {
        return player;
    }

    public BoardArea getBoardArea(){
        return boardArea;
    }

    public Button getReturnButton(){
        return returnButton;
    }

    public boolean isReturnToMenu(){
        return returnToMenuFlag;
    }

    public void update(int mouseX, int mouseY){
        player.checkCardIsTouched(mouseX, mouseY);

        returnToMenuFlag = returnButton.isActive();
    }

    public void draw(SpriteBatch batch) {
        returnButton.draw(batch, "Return");

        boardArea.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);
    }

    public void dispose(){
        player.dispose();
        sumBox.dispose();
        returnButton.dispose();
    }
}
