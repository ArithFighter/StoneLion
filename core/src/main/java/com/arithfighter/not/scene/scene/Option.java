package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.scene.*;
import com.arithfighter.not.widget.a1.ControlBar;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Option extends SceneComponent implements SceneEvent, MouseEvent, OptionEvent {
    private final SceneControlButton leaveButton;
    private final SoundManager soundManager;
    private final ControlBar soundControl;
    private final ControlBar musicControl;
    private GameScene sceneTemp;

    public Option(TextureService textureService, SoundManager soundManager, FontService fontService){
        this.soundManager = soundManager;
        TextureRegion[] textures = textureService.getTextureRegionMap().get(textureService.getKeys()[0]);
        Font font = fontService.getFont22();

        LayoutSetter layout = new LayoutSetter();
        layout.setGrid(5,6);
        Rectangle grid = layout.getGrid();

        leaveButton = new SceneControlButton(textures[6], 1.8f);
        leaveButton.getButton().setFont(font);
        leaveButton.getButton().setPosition(grid.getWidth()*2, grid.getHeight());

        soundControl = new ControlBar(textures, 6);
        soundControl.setFont(font);
        soundControl.setPosition(grid.getWidth()*2, grid.getHeight()*5);

        musicControl = new ControlBar(textures, 6);
        musicControl.setFont(font);
        musicControl.setPosition(grid.getWidth()*2, grid.getHeight()*4);
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x= cursorPos.getX();
        int y = cursorPos.getY();

        soundControl.activate(x, y);
        musicControl.activate(x, y);
        leaveButton.getButton().on(x, y);
    }

    @Override
    public void touchDragged() {
        soundControl.deactivate();
        musicControl.deactivate();
        leaveButton.getButton().off();
    }

    @Override
    public void touchUp() {
        if (soundControl.isButtonActive()|| musicControl.isButtonActive())
            soundManager.playTouchedSound();

        if (leaveButton.getButton().isOn())
            soundManager.playReturnSound();

        soundControl.deactivate();
        musicControl.deactivate();
        leaveButton.getButton().off();
    }

    @Override
    public void init() {
        leaveButton.init();
    }

    @Override
    public void render() {
        update();

        leaveButton.getButton().draw(getBatch(), "Quit");
        soundControl.draw(getBatch(), "SOUND");
        musicControl.draw(getBatch(), "MUSIC");
    }

    private void update(){
        leaveButton.update();
        soundControl.update();
        musicControl.update();
    }

    public GameScene getSceneTemp() {
        return sceneTemp;
    }

    public void setSceneTemp(GameScene sceneTemp) {
        this.sceneTemp = sceneTemp;
    }

    public void setSoundVolume(int i){
        soundControl.setValue(i);
    }

    public void setMusicVolume(int i){
        musicControl.setValue(i);
    }

    public int getSoundVolume(){
        return soundControl.getValue();
    }

    public int getMusicVolume(){
        return musicControl.getValue();
    }

    public boolean isLeaving() {
        return leaveButton.isStart();
    }
}