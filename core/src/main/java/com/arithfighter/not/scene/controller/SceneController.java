package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;

public class SceneController {
    private GameScene gameScene;
    private final SceneControllerService sceneControllerService;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        sceneControllerService = new SceneControllerService(sceneBuilder);
        sceneControllerService.setGameScene(gameScene);
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