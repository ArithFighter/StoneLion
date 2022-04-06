package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.entity.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenu {
    private final SceneControlButton returnButton;

    public OptionMenu(Texture[] textures){
        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(500,120);
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
