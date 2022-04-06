package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.entity.ControlBar;
import com.arithfighter.ccg.entity.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenu {
    private final SceneControlButton returnButton;
    private final ControlBar soundControl;

    public OptionMenu(Texture[] textures){
        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(500,120);

        soundControl = new ControlBar(textures);
        soundControl.setPosition(500,600);
    }

    public int getSoundVolume(){
        return soundControl.getValue();
    }

    public void draw(SpriteBatch batch) {
        soundControl.draw(batch, "Sound");
        returnButton.getButton().draw(batch, "Return");
    }

    public void update() {
        soundControl.update();
        returnButton.handleScene();
    }

    public void init() {
        returnButton.init();
    }

    public boolean isReturnToMainMenu() {
        return returnButton.isStart();
    }

    public void touchDown(float x, float y) {
        soundControl.activate(x,y);
        returnButton.getButton().activate(x, y);
    }

    public void touchDragged(){
        soundControl.deactivate();
        returnButton.getButton().deactivate();
    }

    public void touchUp() {
        soundControl.deactivate();
        returnButton.getButton().deactivate();
    }

    public void dispose() {
        soundControl.dispose();
        returnButton.getButton().dispose();
    }
}
