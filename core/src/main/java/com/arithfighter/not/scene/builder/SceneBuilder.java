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

import java.util.ArrayList;
import java.util.List;

public class SceneBuilder extends SceneCollection {
    private MouseEvent[] mouseEvents;
    private SceneEvent[] sceneEvents;
    private OptionSave optionSave;

    public SceneBuilder(TextureService textureService, SoundManager soundManager, FontService fontService) {
        super(textureService, soundManager, fontService);

        initGameScene();

        initMouseEvents(getSceneModels());

        initSceneEvents(getSceneModels());
    }

    private void initGameScene(){
        GameScene.DECK_SELECTION.setSceneModel(new SceneModel(getDeckSelection(), getDeckSelection()));
        GameScene.TRANSITION.setSceneModel(new SceneModel(getTransition()));
        GameScene.STAGE.setSceneModel(new SceneModel(getStage(), getStage()));
        GameScene.GAME_OVER.setSceneModel(new SceneModel(getGameOver(), getGameOver()));
        GameScene.OPTION.setSceneModel(new SceneModel(getOption(), getOption()));
    }

    private SceneModel[] getSceneModels(){
        List<SceneModel> sceneModelList = new ArrayList<>();
        for (GameScene g:GameScene.values())
            sceneModelList.add(g.getSceneModel());

        SceneModel[] sceneModels = new SceneModel[sceneModelList.size()];
        for (int i = 0; i<sceneModels.length;i++)
            sceneModels[i] = sceneModelList.get(i);

        return sceneModels;
    }

    private void initMouseEvents(SceneModel[] sceneModels){
        List<MouseEvent> mouseEventList = new ArrayList<>();
        for (SceneModel s:sceneModels){
            if (s.getMouseEvent()!=null)
                mouseEventList.add(s.getMouseEvent());
        }
        mouseEvents = new MouseEvent[mouseEventList.size()];
        for (int i = 0; i< mouseEvents.length;i++)
            mouseEvents[i] = mouseEventList.get(i);
    }

    private void initSceneEvents(SceneModel[] sceneModels){
        List<SceneEvent> sceneEventList = new ArrayList<>();
        for (SceneModel s:sceneModels){
            if (s.getSceneEvent()!=null)
                sceneEventList.add(s.getSceneEvent());
        }
        sceneEvents = new SceneEvent[sceneEventList.size()];
        for (int i =0; i< sceneEvents.length;i++)
            sceneEvents[i] = sceneEventList.get(i);
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