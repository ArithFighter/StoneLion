package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;

public class SceneController {
    private GameScene gameScene;
    private final SceneControllerService sceneControllerService;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        sceneControllerService = new SceneControllerService(sceneBuilder);
        sceneControllerService.setGameScene(gameScene);
    }

    public void setGameSave(GameSave gameSave) {
        sceneControllerService.setGameSave(gameSave);
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        for (int i = 0;i<GameScene.values().length;i++){
            if (gameScene == GameScene.values()[i]){
                sceneControllerService.update(i);
                gameScene = sceneControllerService.getGameScene();
            }
        }
    }
}