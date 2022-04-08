package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.CursorPositionAccessor;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.file.MyAssetProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder {
    private final CharacterMenu characterMenu;
    private final Game game;
    private final OptionMenu optionMenu;
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(MyAssetProcessor assetProcessor,
                        SoundManager soundManager,
                        SpriteBatch batch,
                        CursorPositionAccessor cursorPos
    ){
        characterMenu = new CharacterMenu(assetProcessor.getWidgets(), assetProcessor.getPanels(),
                soundManager);

        game = new Game(assetProcessor.getWidgets(), assetProcessor.getCards(),
                soundManager);

        optionMenu = new OptionMenu(assetProcessor.getWidgets(), soundManager);

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

    public void renderScene(int index){
        sceneEvents[index].update();
        sceneEvents[index].draw();
    }

    public CharacterMenu getCharacterMenu() {
        return characterMenu;
    }

    public Game getGame() {
        return game;
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    public void dispose(){
        for (SceneEvent sceneEvent:sceneEvents)
            sceneEvent.dispose();
    }
}
