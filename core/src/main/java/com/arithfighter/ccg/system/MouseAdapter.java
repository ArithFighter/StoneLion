package com.arithfighter.ccg.system;

import com.arithfighter.ccg.GameComponent;
import com.badlogic.gdx.InputAdapter;

public class MouseAdapter extends InputAdapter {
    GameComponent gameComponent;
    int mouseX;
    int mouseY;

    public MouseAdapter(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
    }

    public void updateMousePos(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameComponent.getHand().checkActive(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        gameComponent.getHand().updateWhenDrag(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gameComponent.whenPlayCardOnTable(mouseX, mouseY);
        return true;
    }
}
