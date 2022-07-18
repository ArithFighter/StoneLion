package com.arithfighter.not.scene.controller;

public class SceneControllerService {
    private final SceneFactory[] sceneFactories;
    private final SceneControllable[] sceneManageable;

    public SceneControllerService(SceneControllerCollection scc) {
        sceneFactories = new SceneFactory[]{
                scc.getDeckSelectionController(),
                scc.getEnchantmentMapController(),
                scc.getTransitionController(),
                scc.getStageController(),
                scc.getGameOverController(),
                scc.getOptionController()
        };

        sceneManageable = new SceneControllable[]{
                scc.getDeckSelectionController(),
                scc.getEnchantmentMapController(),
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