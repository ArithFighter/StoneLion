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

class SceneModel{
    private MouseEvent mouseEvent;
    private SceneEvent sceneEvent;

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