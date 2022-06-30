package com.arithfighter.not.scene.builder;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.save.GameSave;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.save.OptionSave;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.scene.SceneModel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.scene.GameScene.*;

public class SceneBuilder extends SceneCollection {
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;
    private OptionSave optionSave;

    public SceneBuilder(TextureService textureService, SoundManager soundManager, FontService fontService) {
        super(textureService, soundManager, fontService);

        initGameScene();

        mouseEvents = new MouseEvent[]{
                DECK_SELECTION.getSceneModel().getMouseEvent(),
                STAGE.getSceneModel().getMouseEvent(),
                GAME_OVER.getSceneModel().getMouseEvent(),
                OPTION.getSceneModel().getMouseEvent()
        };

        sceneEvents = new SceneEvent[]{
                DECK_SELECTION.getSceneModel().getSceneEvent(),
                TRANSITION.getSceneModel().getSceneEvent(),
                STAGE.getSceneModel().getSceneEvent(),
                GAME_OVER.getSceneModel().getSceneEvent(),
                OPTION.getSceneModel().getSceneEvent()
        };
    }

    private void initGameScene(){
        GameScene.DECK_SELECTION.setSceneModel(new SceneModel(getDeckSelection(), getDeckSelection()));
        GameScene.TRANSITION.setSceneModel(new SceneModel(getTransition()));
        GameScene.STAGE.setSceneModel(new SceneModel(getStage(), getStage()));
        GameScene.GAME_OVER.setSceneModel(new SceneModel(getGameOver(), getGameOver()));
        GameScene.OPTION.setSceneModel(new SceneModel(getOption(), getOption()));
    }

    public void setOptionSave(GameSave gameSave) {
        optionSave = new OptionSave(gameSave, getOption());
    }

    public OptionSave getOptionSave() {
        return optionSave;
    }

    public void setBatch(SpriteBatch batch) {
        for (SceneEvent sceneEvent : sceneEvents)
            sceneEvent.setBatch(batch);
    }

    public void setCursorPos(CursorPositionAccessor cursorPos) {
        for (MouseEvent mouseEvent : mouseEvents)
            mouseEvent.setCursorPos(cursorPos);
    }

    public MouseEvent[] getMouseEvents() {
        return mouseEvents;
    }

    public SceneEvent[] getSceneEvents() {
        return sceneEvents;
    }
}