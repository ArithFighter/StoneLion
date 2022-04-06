package com.arithfighter.ccg.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final SceneControlButton returnButton;

    public PauseMenu(Texture[] textures) {
        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(1000, 600);
    }

    public void draw(SpriteBatch batch) {
        returnButton.getButton().draw(batch, "Return");
    }

    public void update() {
        returnButton.handleScene();
    }

    public void init() {
        returnButton.init();
    }

    public boolean isReturnToMainMenu() {
        return returnButton.isStart();
    }

    public void touchDown(float x, float y) {
        returnButton.getButton().activate(x, y);
    }

    public void touchDragged(){
        returnButton.getButton().deactivate();
    }

    public void touchUp() {
        returnButton.getButton().deactivate();
    }

    public void dispose() {
        returnButton.getButton().dispose();
    }
}
