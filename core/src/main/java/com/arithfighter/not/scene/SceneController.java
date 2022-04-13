package com.arithfighter.not.scene;

public class SceneController {
    private final SceneBuilder sceneBuilder;
    private GameScene gameScene = GameScene.MENU;

    public SceneController(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        if (sceneBuilder.getCharacterMenu().isGameStart()) {
            gameScene = GameScene.BET;
            sceneBuilder.getCharacterMenu().init();
        }
        if (sceneBuilder.getCharacterMenu().isOpenOption()) {
            gameScene = GameScene.OPTION;
            sceneBuilder.getCharacterMenu().init();
        }
        if (sceneBuilder.getBetScreen().isStartGame()) {
            gameScene = GameScene.STAGE;
            sceneBuilder.getStage().setInitToken(sceneBuilder.getBetScreen().getInitTokens());
            sceneBuilder.getBetScreen().init();
        }
        if (sceneBuilder.getStage().isReturnToMenu()) {
            gameScene = GameScene.MENU;
            sceneBuilder.getStage().init();
        }
        if (sceneBuilder.getOptionMenu().isReturnToMainMenu()) {
            gameScene = GameScene.MENU;
            sceneBuilder.getOptionMenu().init();
        }
    }
}
