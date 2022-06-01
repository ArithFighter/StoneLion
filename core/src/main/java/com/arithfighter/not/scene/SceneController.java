package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;

public class SceneController {
    private GameScene gameScene;
    private final SceneManager sceneManager;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        sceneManager = new SceneManager(sceneBuilder);
        sceneManager.setGameScene(gameScene);
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