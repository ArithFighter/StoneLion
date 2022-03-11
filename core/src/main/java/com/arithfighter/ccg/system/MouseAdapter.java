package com.arithfighter.ccg.system;

import com.arithfighter.ccg.component.GameComponent;
import com.arithfighter.ccg.component.Player;
import com.badlogic.gdx.InputAdapter;

public class MouseAdapter extends InputAdapter {
    GameComponent gameComponent;
    Player player;
    int mouseX;
    int mouseY;

    public MouseAdapter(GameComponent gameComponent, Player player) {
        this.gameComponent = gameComponent;
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
        gameComponent.playCardOnTable(mouseX, mouseY);
        return true;
    }
}
