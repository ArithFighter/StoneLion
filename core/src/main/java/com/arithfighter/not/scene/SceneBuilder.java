package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.FontService;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(TextureService textureService, SoundManager soundManager, FontService fontService){
        super(textureService, soundManager, fontService);

        mouseEvents = new MouseEvent[]{
                getStage()
        };

        sceneEvents = new SceneEvent[]{
                getStage()
        };
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

    public SceneCollection(TextureService textureService, SoundManager soundManager, FontService fontService){
        stage = new Stage(textureService, soundManager, fontService);
    }

    public Stage getStage() {
        return stage;
    }
}