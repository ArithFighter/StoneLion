package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.CursorPositionAccessor;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.entity.ControlBar;
import com.arithfighter.ccg.entity.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenu implements SceneEvent, MouseEvent{
    private final SceneControlButton returnButton;
    private final ControlBar soundControl;
    private final ControlBar musicControl;
    private final SoundManager soundManager;
    private SpriteBatch batch;
    private CursorPositionAccessor cursorPos;

    public OptionMenu(Texture[] textures, SoundManager soundManager){
        this.soundManager = soundManager;

        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(500,120);

        soundControl = new ControlBar(textures, "Sound");
        soundControl.setPosition(500,600);

        musicControl = new ControlBar(textures, "Music");
        musicControl.setPosition(500,400);
    }

    public int getSoundVolume(){
        return soundControl.getValue();
    }

    public int getMusicVolume(){
        return musicControl.getValue();
    }

    public void draw() {
        soundControl.draw(batch);
        musicControl.draw(batch);
        returnButton.getButton().draw(batch, "Return");
    }

    public void update() {
        soundControl.update();
        musicControl.update();
        returnButton.handleScene();
    }

    @Override
    public void setCursorPos(CursorPositionAccessor cursorPos) {
        this.cursorPos = cursorPos;
    }

    @Override
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void init() {
        returnButton.init();
    }

    public boolean isReturnToMainMenu() {
        return returnButton.isStart();
    }

    public void touchDown() {
        soundControl.activate(cursorPos.getX(), cursorPos.getY());
        musicControl.activate(cursorPos.getX(), cursorPos.getY());
        returnButton.getButton().activate(cursorPos.getX(), cursorPos.getY());
    }

    public void touchDragged(){
        soundControl.deactivate();
        musicControl.deactivate();
        returnButton.getButton().deactivate();
    }

    public void touchUp() {
        if (soundControl.isButtonActive()|| musicControl.isButtonActive())
            soundManager.playTouchedSound();

        if (returnButton.getButton().isActive())
            soundManager.playReturnSound();

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
