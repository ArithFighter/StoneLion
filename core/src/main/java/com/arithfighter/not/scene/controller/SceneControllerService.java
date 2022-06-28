package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.builder.SceneBuilder;

public class SceneControllerService {
    private final SceneFactory[] sceneFactories;
    private final SceneControllable[] sceneManageable;

    public SceneControllerService(SceneBuilder sceneBuilder) {
        SceneControllerCollection scc = new SceneControllerCollection(sceneBuilder);

        sceneFactories = new SceneFactory[]{
                scc.getDeckSelectionController(),
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getOptionController()
        };

        sceneManageable = new SceneControllable[]{
                scc.getDeckSelectionController(),
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getOptionController()
        };

        for (SceneControllable s : sceneManageable)
            s.initScene();
    }

    public SceneFactory[] getSceneFactories() {
        return sceneFactories;
    }

    public SceneControllable[] getSceneManageable() {
        return sceneManageable;
    }
}