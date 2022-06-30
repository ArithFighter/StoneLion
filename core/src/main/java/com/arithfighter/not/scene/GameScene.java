package com.arithfighter.not.scene;

public enum GameScene {
    DECK_SELECTION,
    TRANSITION,
    STAGE,
    GAME_OVER,
    OPTION;

    private SceneModel sceneModel;

    public SceneModel getSceneModel() {
        return sceneModel;
    }

    public void setSceneModel(SceneModel sceneModel) {
        this.sceneModel = sceneModel;
    }
}

