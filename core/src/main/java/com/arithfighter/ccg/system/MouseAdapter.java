package com.arithfighter.ccg.system;

import com.arithfighter.ccg.component.CardTable;
import com.arithfighter.ccg.component.Player;
import com.badlogic.gdx.InputAdapter;

public class MouseAdapter extends InputAdapter {
    private final CardTable cardTable;
    private final Player player;
    int mouseX;
    int mouseY;

    public MouseAdapter(CardTable cardTable, Player player) {
        this.cardTable = cardTable;
        this.player = player;
    }

    public void updateMousePos(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.activateCard(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        player.updateWhenDrag(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        cardTable.playCardOnTable(mouseX, mouseY);
        return true;
    }
}
