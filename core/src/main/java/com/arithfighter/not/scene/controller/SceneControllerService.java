package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.builder.SceneBuilder;
import com.arithfighter.not.scene.builder.SceneCollection;

class SceneControllerService {
    private final SceneFactory[] sceneFactories;
    private final SceneControllable[] sceneManageable;

    public SceneControllerService(SceneCollection sceneCollection) {
        SceneControllerCollection scc = new SceneControllerCollection(sceneCollection);

        sceneFactories = new SceneFactory[]{
                scc.getDeckSelectionController(),
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getGameOverController(),
                scc.getOptionController()
        };

        sceneManageable = new SceneControllable[]{
                scc.getDeckSelectionController(),
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getGameOverController(),
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