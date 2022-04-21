package com.arithfighter.not.scene;

import com.arithfighter.GameSave;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.file.MyAssetProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(MyAssetProcessor assetProcessor, SoundManager soundManager, GameSave gameSave){
        super(assetProcessor, soundManager, gameSave);

        mouseEvents = new MouseEvent[]{
                getCharacterMenu(),
                getBetScreen(),
                getStage(),
                getResultScreen(),
                getGameOver(),
                getOptionMenu()
        };

        sceneEvents = new SceneEvent[]{
                getCharacterMenu(),
                getBetScreen(),
                getStage(),
                getResultScreen(),
                getGameOver(),
                getOptionMenu()
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

    public void updateScene(GameScene gameScene){
        for (int i = 0; i < GameScene.values().length; i++)
            if (gameScene == GameScene.values()[i]){
                sceneEvents[i].update();
            }
    }

    public void drawScene(GameScene gameScene) {
        for (int i = 0; i < GameScene.values().length; i++)
            if (gameScene == GameScene.values()[i]){
                sceneEvents[i].draw();
            }
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
    private final ResultScreen resultScreen;
    private final GameOver gameOver;

    public SceneCollection(MyAssetProcessor assetProcessor, SoundManager soundManager, GameSave gameSave){
        characterMenu = new CharacterMenu(assetProcessor.getWidgets(), assetProcessor.getPanels(),
                soundManager);

        stage = new Stage(assetProcessor.getWidgets(), assetProcessor.getCards(),
                soundManager);

        optionMenu = new OptionMenu(assetProcessor.getWidgets(), soundManager, gameSave);

        betScreen = new BetScreen(assetProcessor.getWidgets(), soundManager);

        resultScreen = new ResultScreen(assetProcessor.getWidgets());

        gameOver = new GameOver(assetProcessor.getWidgets());
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

    public ResultScreen getResultScreen(){
        return resultScreen;
    }

    public GameOver getGameOver(){
        return gameOver;
    }
}