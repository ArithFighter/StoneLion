package com.arithfighter.ccg.system;

import com.arithfighter.ccg.GameComponent;
import com.badlogic.gdx.InputAdapter;

public class MouseAdapter extends InputAdapter {
    GameComponent gameComponent;
    int mouseX;
    int mouseY;
    int energy;

    public MouseAdapter(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
    }

    public void updateMousePos(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void updateEnergy(int energy){
        this.energy = energy;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameComponent.getPlayer().activateCard(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        gameComponent.getPlayer().updateWhenDrag(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gameComponent.playCardOnTable(mouseX, mouseY, energy);
        return true;
    }
}
