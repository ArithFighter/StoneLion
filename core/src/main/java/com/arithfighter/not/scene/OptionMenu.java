package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.ControlBar;
import com.arithfighter.not.entity.SceneControlButton;
import com.arithfighter.not.pojo.TextProvider;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenu implements SceneEvent, MouseEvent{
    private final SceneControlButton returnButton;
    private final ControlBar soundControl;
    private final ControlBar musicControl;
    private final SoundManager soundManager;
    private SpriteBatch batch;
    private CursorPositionAccessor cursorPos;
    private final TextProvider textProvider;

    public OptionMenu(Texture[] textures, SoundManager soundManager){
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        returnButton = new SceneControlButton(textures[6], 1.8f);
        returnButton.getButton().setPosition(500,120);

        soundControl = new ControlBar(textures, 6);
        soundControl.setPosition(500,600);

        musicControl = new ControlBar(textures, 6);
        musicControl.setPosition(500,400);
    }

    public int getSoundVolume(){
        return soundControl.getValue();
    }

    public int getMusicVolume(){
        return musicControl.getValue();
    }

    public void draw() {
        String[] texts = textProvider.getOptionMenuTexts();

        soundControl.draw(batch,texts[0]);
        musicControl.draw(batch,texts[1]);
        returnButton.getButton().draw(batch, texts[2]);
    }

    public void update() {
        soundControl.update();
        musicControl.update();
        returnButton.update();
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
