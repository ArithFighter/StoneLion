package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;

public class SceneController {
    private GameScene gameScene;
    private final SceneBuilder sceneBuilder;
    private final GameRecorder gameRecorder;
    private final SceneManager sceneManager;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        gameRecorder = new GameRecorder();

        this.sceneBuilder = sceneBuilder;

        sceneManager = new SceneManager(sceneBuilder, gameRecorder);
        sceneManager.setGameScene(gameScene);
    }

    public void setGameSave(GameSave gameSave) {
        sceneManager.setGameSave(gameSave);
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        Stage stage = sceneBuilder.getStage();
        stage.getRecordDisplacer().setGameRecorder(gameRecorder);

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
                GameOver gameOver = sceneBuilder.getGameOver();

                if (gameOver.isQuit()) {
                    gameScene = GameScene.MENU;
                    gameOver.init();
                }
                break;
            case ENDING:
                Ending ending = sceneBuilder.getEnding();
                if (ending.isLeave()) {
                    gameScene = GameScene.MENU;
                    ending.init();
                }
                break;
            case OPTION:
                sceneManager.manageOption();
                gameScene = sceneManager.getGameScene();
                break;
        }
    }
}