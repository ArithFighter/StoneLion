package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.save.GameSave;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.save.OptionSave;
import com.arithfighter.not.scene.scene.Option;
import com.arithfighter.not.scene.scene.Stage;
import com.arithfighter.not.scene.scene.Transition;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;
    private OptionSave optionSave;

    public SceneBuilder(TextureService textureService, SoundManager soundManager, FontService fontService){
        super(textureService, soundManager, fontService);

        mouseEvents = new MouseEvent[]{
                getTransition(),
                getStage(),
                getOption()
        };

        sceneEvents = new SceneEvent[]{
                getTransition(),
                getStage(),
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
        for (SceneEvent sceneEvent:sceneEvents){
            sceneEvent.setBatch(batch);
        }
    }

    public void setCursorPos(CursorPositionAccessor cursorPos){
        for (MouseEvent mouseEvent:mouseEvents){
            mouseEvent.setCursorPos(cursorPos);
        }
    }

    public MouseEvent[] getMouseEvents(){
        return mouseEvents;
    }

    public void renderScene(GameScene gameScene) {
        for (int i = 0; i < GameScene.values().length; i++)
            if (gameScene == GameScene.values()[i]){
                sceneEvents[i].render();
            }
    }
}

class SceneCollection{
    private final Stage stage;
    private final Transition transition;
    private final Option option;

    public SceneCollection(TextureService textureService, SoundManager soundManager, FontService fontService){
        stage = new Stage(textureService, soundManager, fontService);
        transition = new Transition(fontService);
        option = new Option(textureService, soundManager, fontService);
    }

    public Stage getStage() {
        return stage;
    }

    public Transition getTransition() {
        return transition;
    }

    public Option getOption() {
        return option;
    }
}