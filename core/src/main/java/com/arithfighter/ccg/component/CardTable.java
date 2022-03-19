package com.arithfighter.ccg.component;

import com.arithfighter.ccg.widget.BoardArea;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class CardTable {
    private final BoardArea boardArea;

    public CardTable(Texture[] textures){
        boardArea = new BoardArea(textures[1]);
        boardArea.setPosition(CENTER_X + GRID_X * 4, GRID_Y * 6);
    }

    public void draw(SpriteBatch batch){
        boardArea.draw(batch);
    }

    public final void playCardOnTable(int mouseX, int mouseY) {
        if (boardArea.isOnBoard(mouseX, mouseY)) {
            checkCardPlayed();
        }
        initCardPosition();
    }

    public void initCardPosition() {

    }

    public void checkCardPlayed() {

    }
}


