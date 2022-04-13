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
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        if (sceneBuilder.getCharacterMenu().isGameStart()) {
            gameScene = GameScene.BET;
            sceneBuilder.getBetScreen().setToken(sceneBuilder.getStage().getTokens());
            sceneBuilder.getCharacterMenu().init();
        }
        if (sceneBuilder.getCharacterMenu().isOpenOption()) {
            gameScene = GameScene.OPTION;
            sceneBuilder.getCharacterMenu().init();
        }
        if (sceneBuilder.getBetScreen().isStartGame()) {
            gameScene = GameScene.STAGE;
            sceneBuilder.getStage().setNumberBoxQuantity(sceneBuilder.getBetScreen().getNumberBoxQuantity());
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
