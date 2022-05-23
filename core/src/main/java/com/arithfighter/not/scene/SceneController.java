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
        switch (gameScene) {
            case MENU:
                sceneManager.manageMenu();
                gameScene = sceneManager.getGameScene();
                break;
            case BET:
                sceneManager.manageBet();
                gameScene = sceneManager.getGameScene();
                break;
            case STAGE:
                sceneManager.manageStage();
                gameScene = sceneManager.getGameScene();
                break;
            case RESULT:
                sceneManager.manageResult();
                gameScene = sceneManager.getGameScene();
                break;
            case GAME_OVER:
                sceneManager.manageGameOver();
                gameScene = sceneManager.getGameScene();
                break;
            case ENDING:
                sceneManager.manageEnding();
                gameScene = sceneManager.getGameScene();
                break;
            case OPTION:
                sceneManager.manageOption();
                gameScene = sceneManager.getGameScene();
                break;
        }
    }
}