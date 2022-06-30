package com.arithfighter.not.scene;

public class SceneModel {
    private MouseEvent mouseEvent;
    private SceneEvent sceneEvent;

    public SceneModel() {
    }

    public SceneModel(SceneEvent sceneEvent) {
        this.sceneEvent = sceneEvent;
    }

    public SceneModel(MouseEvent mouseEvent, SceneEvent sceneEvent) {
        this.mouseEvent = mouseEvent;
        this.sceneEvent = sceneEvent;
    }

    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public void setSceneEvent(SceneEvent sceneEvent) {
        this.sceneEvent = sceneEvent;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public SceneEvent getSceneEvent() {
        return sceneEvent;
    }
}
