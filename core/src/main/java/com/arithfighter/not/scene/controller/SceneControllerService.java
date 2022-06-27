package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;

public class SceneControllerService {
    private GameScene gameScene;
    private final SceneFactory[] sceneFactories;
    private final SceneControllable[] sceneManageable;

    public SceneControllerService(SceneBuilder sceneBuilder) {
        SceneControllerCollection scc = new SceneControllerCollection(sceneBuilder);

        sceneFactories = new SceneFactory[]{
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getOptionController()
        };

        sceneManageable = new SceneControllable[]{
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getOptionController()
        };

        for (SceneControllable s : sceneManageable)
            s.initScene();
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void update(int index) {
        for (SceneControllable s : sceneManageable)
            s.initScene();

        sceneManageable[index].run();

        gameScene = sceneFactories[index].getGamaScene();
    }
}