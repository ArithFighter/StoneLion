package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.file.MyAssetProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(MyAssetProcessor assetProcessor, SoundManager soundManager){
        super(assetProcessor, soundManager);

        mouseEvents = new MouseEvent[]{
                getCharacterMenu(),
                getBetScreen(),
                getStage(),
                getOptionMenu()
        };

        sceneEvents = new SceneEvent[]{
                getCharacterMenu(),
                getBetScreen(),
                getStage(),
                getOptionMenu()
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

    public void dispose(){
        for (SceneEvent sceneEvent:sceneEvents)
            sceneEvent.dispose();
    }
}

class SceneCollection{
    private final CharacterMenu characterMenu;
    private final Stage stage;
    private final OptionMenu optionMenu;
    private final BetScreen betScreen;

    public SceneCollection(MyAssetProcessor assetProcessor, SoundManager soundManager){
        characterMenu = new CharacterMenu(assetProcessor.getWidgets(), assetProcessor.getPanels(),
                soundManager);

        stage = new Stage(assetProcessor.getWidgets(), assetProcessor.getCards(),
                soundManager);

        optionMenu = new OptionMenu(assetProcessor.getWidgets(), soundManager);

        betScreen = new BetScreen(assetProcessor.getWidgets());
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

    public BetScreen getBetScreen(){
        return betScreen;
    }
}