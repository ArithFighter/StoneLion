package com.arithfighter.not.scene.controller;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;

class BuilderAccessor implements SceneFactory {
    private final SceneBuilder sceneBuilder;
    private GameScene gameScene;

    public BuilderAccessor(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public GameScene getGamaScene() {
        return gameScene;
    }
}
