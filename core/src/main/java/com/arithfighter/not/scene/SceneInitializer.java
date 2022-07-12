package com.arithfighter.not.scene;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.SceneModel;
import com.arithfighter.not.scene.builder.SceneCollection;

public class SceneInitializer {
    private final SceneCollection sceneCollection;

    public SceneInitializer(SceneCollection sceneCollection) {
        this.sceneCollection = sceneCollection;
    }

    public void run() {
        GameScene.DECK_SELECTION.setSceneModel(
                new SceneModel(sceneCollection.getDeckSelection(), sceneCollection.getDeckSelection()));

        GameScene.TRANSITION.setSceneModel(
                new SceneModel(sceneCollection.getTransition()));

        GameScene.STAGE.setSceneModel(
                new SceneModel(sceneCollection.getStage(), sceneCollection.getStage()));

        GameScene.GAME_OVER.setSceneModel(
                new SceneModel(sceneCollection.getGameOver(), sceneCollection.getGameOver()));

        GameScene.OPTION.setSceneModel(
                new SceneModel(sceneCollection.getOption(), sceneCollection.getOption()));
    }
}
