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
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        Stage stage = sceneBuilder.getStage();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (characterMenu.isGameStart()) {
            gameScene = GameScene.BET;
            betScreen.setToken(stage.getTokens());
            characterMenu.init();
        }
        if (characterMenu.isOpenOption()) {
            gameScene = GameScene.OPTION;
            characterMenu.init();
        }
        if (betScreen.isStartGame()) {
            gameScene = GameScene.STAGE;
            stage.setNumberBoxQuantity(betScreen.getNumberBoxQuantity());
            betScreen.init();
        }
        if (stage.isReturnToMenu()) {
            gameScene = GameScene.MENU;
            stage.init();
        }
        if (optionMenu.isReturnToMainMenu()) {
            gameScene = GameScene.MENU;
            optionMenu.init();
        }
    }
}
