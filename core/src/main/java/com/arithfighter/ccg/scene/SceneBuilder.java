package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.CursorPositionAccessor;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.file.MyAssetProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder {
    private final CharacterMenu characterMenu;
    private final Stage stage;
    private final OptionMenu optionMenu;
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(MyAssetProcessor assetProcessor, SoundManager soundManager){
        characterMenu = new CharacterMenu(assetProcessor.getWidgets(), assetProcessor.getPanels(),
                soundManager);

        stage = new Stage(assetProcessor.getWidgets(), assetProcessor.getCards(),
                soundManager);

        optionMenu = new OptionMenu(assetProcessor.getWidgets(), soundManager);

        mouseEvents = new MouseEvent[]{
                characterMenu,
                stage,
                optionMenu
        };

        sceneEvents = new SceneEvent[]{
                characterMenu,
                stage,
                optionMenu
        };
    }

    public void setBatch(SpriteBatch batch){
        for (SceneEvent sceneEvent:sceneEvents){
            sceneEvent.setBatch(batch);
        }
    }

    public void setCursorPos(CursorPositionAccessor cursorPos){
        for (SceneEvent sceneEvent:sceneEvents){
            sceneEvent.setCursorPos(cursorPos);
        }
    }

    public MouseEvent[] getMouseEvents(){
        return mouseEvents;
    }

    public SceneEvent[] getSceneEvents(){
        return sceneEvents;
    }

    public void renderScene(int index){
        sceneEvents[index].update();
        sceneEvents[index].draw();
    }

    public CharacterMenu getCharacterMenu() {
        return characterMenu;
    }

    public Stage getStage() {
        return stage;
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    public void dispose(){
        for (SceneEvent sceneEvent:sceneEvents)
            sceneEvent.dispose();
    }
}
