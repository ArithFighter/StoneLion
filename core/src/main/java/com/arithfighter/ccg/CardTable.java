package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.Board;
import com.arithfighter.ccg.widget.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class CardTable {
    Board board;
    SumBox sumBox;

    public CardTable(Texture[] textures){
        board = new Board(textures[1], CENTER_X + GRID_X * 4, GRID_Y * 6);

        sumBox = new SumBox(textures[2], CENTER_X + GRID_X * 8, GRID_Y * 7);
    }

    public void draw(SpriteBatch batch, int sum, int condition){
        board.draw(batch);

        sumBox.draw(sum, condition, batch);
    }

    public boolean isCardOnBoard(float mouseX, float mouseY){
        return board.isOnBoard(mouseX, mouseY);
    }

    public void dispose(){
        sumBox.dispose();
    }
}
