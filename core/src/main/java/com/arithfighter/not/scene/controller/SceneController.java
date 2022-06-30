package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneCollection;

public class SceneController {
    private GameScene gameScene;
    private final SceneControllerService sceneControllerService;

    public SceneController(SceneCollection sceneCollection, GameScene initScene) {
        gameScene = initScene;

        sceneControllerService = new SceneControllerService(sceneCollection);
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