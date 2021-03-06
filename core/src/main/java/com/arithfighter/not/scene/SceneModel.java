package com.arithfighter.not.scene;

public class SceneModel {
    private MouseEvent mouseEvent;
    private final SceneEvent sceneEvent;

    public SceneModel(SceneEvent sceneEvent) {
        this.sceneEvent = sceneEvent;
    }

    public SceneModel(MouseEvent mouseEvent, SceneEvent sceneEvent) {
        this.mouseEvent = mouseEvent;
        this.sceneEvent = sceneEvent;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public SceneEvent getSceneEvent() {
        return sceneEvent;
    }
}
