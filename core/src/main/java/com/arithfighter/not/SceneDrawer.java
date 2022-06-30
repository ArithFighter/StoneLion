package com.arithfighter.not;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.SceneEvent;

class SceneDrawer {
    private final SceneEvent[] sceneEvents;

    public SceneDrawer(SceneEvent[] sceneEvents) {
        this.sceneEvents = sceneEvents;
    }

    public void draw(GameScene gameScene) {
        for (int i = 0; i < GameScene.values().length; i++) {
            if (gameScene == GameScene.values()[i])
                sceneEvents[i].render();
        }
    }
}
