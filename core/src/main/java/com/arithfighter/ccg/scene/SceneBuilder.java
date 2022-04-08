package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.CursorPositionAccessor;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.file.MyAssetProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder {
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(MyAssetProcessor assetProcessor,
                        SoundManager soundManager,
                        SpriteBatch batch,
                        CursorPositionAccessor cursorPos
    ){
        CharacterMenu characterMenu = new CharacterMenu(
                assetProcessor.getWidgets(),
                assetProcessor.getPanels(),
                soundManager
        );

        Game game = new Game(
                assetProcessor.getWidgets(),
                assetProcessor.getCards(),
                soundManager
        );

        OptionMenu optionMenu = new OptionMenu(assetProcessor.getWidgets(), soundManager);

        mouseEvents = new MouseEvent[]{
                characterMenu,
                game,
                optionMenu
        };

        sceneEvents = new SceneEvent[]{
                characterMenu,
                game,
                optionMenu
        };

        for (SceneEvent sceneEvent:sceneEvents){
            sceneEvent.setBatch(batch);
            sceneEvent.setCursorPos(cursorPos);
        }
    }

    public MouseEvent[] getMouseEvents(){
        return mouseEvents;
    }

    public SceneEvent[] getSceneEvents(){
        return sceneEvents;
    }

    public void dispose(){
        for (SceneEvent sceneEvent:sceneEvents)
            sceneEvent.dispose();
    }
}
