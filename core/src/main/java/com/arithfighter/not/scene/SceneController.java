package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;

public class SceneController {
    private GameScene gameScene;
    private final SceneManager sceneManager;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        GameRecorder gameRecorder = new GameRecorder();

        sceneManager = new SceneManager(sceneBuilder, gameRecorder);
        sceneManager.setGameScene(gameScene);

        Stage stage = sceneBuilder.getStage();
        stage.getRecordDisplacer().setGameRecorder(gameRecorder);
    }

    public void setGameSave(GameSave gameSave) {
        sceneManager.setGameSave(gameSave);
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        for (int i = 0;i<GameScene.values().length;i++){
            if (gameScene == GameScene.values()[i]){
                sceneManager.update(i);
                gameScene = sceneManager.getGameScene();
            }
        }
    }
}