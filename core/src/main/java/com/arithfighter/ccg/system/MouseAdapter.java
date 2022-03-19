package com.arithfighter.ccg.system;

import com.arithfighter.ccg.Game;
import com.badlogic.gdx.InputAdapter;

public class MouseAdapter extends InputAdapter {
    private final Game game;
    int mouseX;
    int mouseY;

    public MouseAdapter(Game game) {
        this.game = game;
    }

    public void updateMousePos(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.getPlayer().activateCard(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        game.getPlayer().updateWhenDrag(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        game.getBoardArea().playCardOnBoard(mouseX, mouseY);
        return true;
    }
}
