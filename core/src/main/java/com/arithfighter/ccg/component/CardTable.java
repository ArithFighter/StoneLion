package com.arithfighter.ccg.component;

import com.arithfighter.ccg.widget.BoardArea;
import com.arithfighter.ccg.widget.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class CardTable {
    private final BoardArea boardArea;
    private final SumBox sumBox;

    public CardTable(Texture[] textures){
        boardArea = new BoardArea(textures[1]);
        boardArea.setPosition(CENTER_X + GRID_X * 4, GRID_Y * 6);

        sumBox = new SumBox(textures[2]);
        sumBox.setPosition(CENTER_X + GRID_X * 8, GRID_Y * 7);
    }

    public void draw(SpriteBatch batch, int sum, int condition){
        boardArea.draw(batch);

        sumBox.draw(sum, condition, batch);
    }

    public final void playCardOnTable(int mouseX, int mouseY) {
        if (isCardOnBoard(mouseX, mouseY)) {
            checkCardPlayed();
        }
        initCardPosition();
    }

    public void initCardPosition() {

    }

    public void checkCardPlayed() {

    }

    private boolean isCardOnBoard(float mouseX, float mouseY){
        return boardArea.isOnBoard(mouseX, mouseY);
    }

    public void dispose(){
        sumBox.dispose();
    }
}


