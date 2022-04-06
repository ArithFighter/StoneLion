package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.entity.ControlBar;
import com.arithfighter.ccg.entity.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenu {
    private final SceneControlButton returnButton;
    private final ControlBar soundControl;
    private final ControlBar musicControl;

    public OptionMenu(Texture[] textures){
        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(500,120);

        soundControl = new ControlBar(textures);
        soundControl.setPosition(500,600);

        musicControl = new ControlBar(textures);
        musicControl.setPosition(500,400);
    }

    public int getSoundVolume(){
        return soundControl.getValue();
    }

    public int getMusicVolume(){
        return musicControl.getValue();
    }

    public void draw(SpriteBatch batch) {
        soundControl.draw(batch, "Sound");
        musicControl.draw(batch, "Music");
        returnButton.getButton().draw(batch, "Return");
    }

    public void update() {
        soundControl.update();
        musicControl.update();
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
        musicControl.activate(x, y);
        returnButton.getButton().activate(x, y);
    }

    public void touchDragged(){
        soundControl.deactivate();
        musicControl.deactivate();
        returnButton.getButton().deactivate();
    }

    public void touchUp() {
        soundControl.deactivate();
        musicControl.deactivate();
        returnButton.getButton().deactivate();
    }

    public void dispose() {
        soundControl.dispose();
        musicControl.dispose();
        returnButton.getButton().dispose();
    }
}
