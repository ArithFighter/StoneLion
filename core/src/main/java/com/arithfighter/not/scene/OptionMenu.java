package com.arithfighter.not.scene;

import com.arithfighter.GameSave;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.widget.ControlBar;
import com.arithfighter.not.widget.SceneControlButton;
import com.arithfighter.not.pojo.TextProvider;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenu extends SceneComponent implements SceneEvent, MouseEvent{
    private final SceneControlButton leaveButton;
    private final ControlBar soundControl;
    private final ControlBar musicControl;
    private final SoundManager soundManager;
    private final TextProvider textProvider;
    private GameScene sceneTemp;
    private GameSave gameSave;

    public OptionMenu(Texture[] textures, SoundManager soundManager){
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        leaveButton = new SceneControlButton(textures[6], 1.8f);
        leaveButton.getButton().setPosition(500,120);

        soundControl = new ControlBar(textures, 6);
        soundControl.setPosition(500,600);
        soundControl.setValue(gameSave.getPreferences().getInteger(gameSave.getOptionKeys()[0]));

        musicControl = new ControlBar(textures, 6);
        musicControl.setPosition(500,400);
        musicControl.setValue(gameSave.getPreferences().getInteger(gameSave.getOptionKeys()[1]));
    }

    public void setGameSave(GameSave gameSave){
        this.gameSave = gameSave;
    }

    public GameScene getSceneTemp(){
        return sceneTemp;
    }

    public void setSceneTemp(GameScene scene){
        sceneTemp = scene;
    }

    public int getSoundVolume(){
        return soundControl.getValue();
    }

    public int getMusicVolume(){
        return musicControl.getValue();
    }

    public void draw() {
        String[] texts = textProvider.getOptionMenuTexts();
        SpriteBatch batch = getBatch();

        soundControl.draw(batch,texts[0]);
        musicControl.draw(batch,texts[1]);
        leaveButton.getButton().draw(batch, texts[2]);
    }

    public void update() {
        soundControl.update();
        musicControl.update();
        leaveButton.update();
    }

    public void init() {
        leaveButton.init();
    }

    public boolean isLeaving() {
        return leaveButton.isStart();
    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x= cursorPos.getX();
        int y = cursorPos.getY();

        soundControl.activate(x, y);
        musicControl.activate(x, y);
        leaveButton.getButton().activate(x, y);
    }

    public void touchDragged(){
        soundControl.deactivate();
        musicControl.deactivate();
        leaveButton.getButton().deactivate();
    }

    public void touchUp() {
        if (soundControl.isButtonActive()|| musicControl.isButtonActive())
            soundManager.playTouchedSound();

        if (leaveButton.getButton().isActive())
            soundManager.playReturnSound();

        soundControl.deactivate();
        musicControl.deactivate();
        leaveButton.getButton().deactivate();
    }

    public void dispose() {
        soundControl.dispose();
        musicControl.dispose();
        leaveButton.getButton().dispose();
    }
}
