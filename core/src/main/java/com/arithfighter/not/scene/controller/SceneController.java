package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.GameScene;

public class SceneController {
    private GameScene gameScene;
    private final SceneControllerService sceneControllerService;

    public SceneController(SceneControllerService service, GameScene initScene) {
        gameScene = initScene;

        sceneControllerService = service;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        for (int i = 0;i<GameScene.values().length;i++){
            if (gameScene == GameScene.values()[i]){
                for (SceneControllable s : sceneControllerService.getSceneManageable())
                    s.initScene();

                sceneControllerService.getSceneManageable()[i].run();

                gameScene = sceneControllerService.getSceneFactories()[i].getGamaScene();
            }
        }
    }
}