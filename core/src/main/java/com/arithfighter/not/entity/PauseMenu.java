package com.arithfighter.not.entity;

import com.arithfighter.not.audio.SoundManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final SceneControlButton returnButton;
    private final SoundManager soundManager;

    public PauseMenu(Texture[] textures, SoundManager soundManager) {
        this.soundManager = soundManager;

        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(600, 500);
    }

    public void draw(SpriteBatch batch) {
        returnButton.getButton().draw(batch, "Return");
    }

    public void update() {
        returnButton.update();
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
        if (returnButton.getButton().isActive())
            soundManager.playReturnSound();

        returnButton.getButton().deactivate();
    }

    public void dispose() {
        returnButton.dispose();
    }
}
