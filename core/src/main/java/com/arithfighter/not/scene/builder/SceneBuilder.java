package com.arithfighter.not.scene.builder;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.save.GameSave;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.save.OptionSave;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;
    private OptionSave optionSave;

    public SceneBuilder(TextureService textureService, SoundManager soundManager, FontService fontService){
        super(textureService, soundManager, fontService);

        mouseEvents = new MouseEvent[]{
                getDeckSelection(),
                getTransition(),
                getStage(),
                getGameOver(),
                getOption()
        };

        sceneEvents = new SceneEvent[]{
                getDeckSelection(),
                getTransition(),
                getStage(),
                getGameOver(),
                getOption()
        };
    }

    public void setOptionSave(GameSave gameSave) {
        optionSave = new OptionSave(gameSave, getOption());
    }

    public OptionSave getOptionSave() {
        return optionSave;
    }

    public void setBatch(SpriteBatch batch){
        for (SceneEvent sceneEvent:sceneEvents)
            sceneEvent.setBatch(batch);
    }

    public void setCursorPos(CursorPositionAccessor cursorPos){
        for (MouseEvent mouseEvent:mouseEvents)
            mouseEvent.setCursorPos(cursorPos);
    }

    public MouseEvent[] getMouseEvents(){
        return mouseEvents;
    }

    public SceneEvent[] getSceneEvents() {
        return sceneEvents;
    }
}