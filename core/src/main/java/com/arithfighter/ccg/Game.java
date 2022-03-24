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
    private final GameDataAccessor dataAccessor;
    private BoardArea boardArea;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private int cursorX;
    private int cursorY;
    private final Button returnButton;
    private boolean returnToMenuFlag = false;

    public Game(Texture[] textures, GameDataAccessor dataAccessor){
        this.dataAccessor = dataAccessor;

        createBoardArea(textures);

        numberBoxDisplacer = new NumberBoxDisplacer(textures[3]) {
            @Override
            public void doWhenSumAndNumMatched() {
                dataAccessor.updateScore(1);
            }
        };

        sumBox = new SumBox(textures[2]);
        sumBox.setPosition(CENTER_X + GRID_X * 8, GRID_Y * 7);

        returnButton = new Button(textures[6]);
        returnButton.setPosition(1000, 600);
    }

    public void setPlayer(Player player){
        this.player = player;
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
        numberBoxDisplacer.refresh();
        dataAccessor.resetRecorder();
        player.init();
    }

    public NumberBoxDisplacer getNumberBoxDisplacer(){
        return numberBoxDisplacer;
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
        cursorX = mouseX;
        cursorY = mouseY;

        numberBoxDisplacer.update(player.getSum());

        player.checkCardIsTouched(mouseX, mouseY);

        returnToMenuFlag = returnButton.isActive();

        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            refresh();
        }
    }

    private void refresh(){
        dataAccessor.resetRecorder();
        numberBoxDisplacer.refresh();
    }

    public void draw(SpriteBatch batch) {
        returnButton.draw(batch, "Return");

        dataAccessor.draw(cursorX, cursorY, player.getEnergy(), batch);//for dev

        boardArea.draw(batch);

        numberBoxDisplacer.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);
    }

    public void dispose(){
        dataAccessor.dispose();
        numberBoxDisplacer.dispose();
        sumBox.dispose();
        returnButton.dispose();
    }
}
