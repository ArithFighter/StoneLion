package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneCollection;

class SceneAccessor implements SceneFactory {
    private final SceneCollection sceneCollection;
    private GameScene gameScene;

    public SceneAccessor(SceneCollection sceneCollection) {
        this.sceneCollection = sceneCollection;
    }

    public SceneCollection getSceneCollection() {
        return sceneCollection;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public GameScene getGamaScene() {
        return gameScene;
    }
}
