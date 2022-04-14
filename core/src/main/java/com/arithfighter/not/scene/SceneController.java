package com.arithfighter.not.scene;

public class SceneController {
    private final SceneBuilder sceneBuilder;
    private GameScene gameScene;

    public SceneController(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;

        gameScene = GameScene.MENU;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        Stage stage = sceneBuilder.getStage();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        if (characterMenu.isGameStart()) {
            gameScene = GameScene.BET;
            betScreen.setToken(stage.getTokenHolder().getToken());
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
        if (stage.isAllNumZero()){
            gameScene = GameScene.RESULT;
            resultScreen.setState(ResultState.WIN);
            stage.getTokenHolder().obtainToken(betScreen.getBet());
            resultScreen.setRemainingTokens(stage.getTokenHolder().getToken());
            stage.init();
        }
        if (stage.isExceedCardLimit(betScreen.getCardLimit())&& gameScene == GameScene.STAGE){
            gameScene = GameScene.RESULT;
            resultScreen.setState(ResultState.LOOSE);
            stage.getTokenHolder().lostToken(betScreen.getBet());
            resultScreen.setRemainingTokens(stage.getTokenHolder().getToken());
            stage.init();
        }
        if (resultScreen.isContinue()){
            gameScene = GameScene.BET;
            betScreen.setToken(stage.getTokenHolder().getToken());
            resultScreen.init();
        }
        if (optionMenu.isReturnToMainMenu()) {
            gameScene = GameScene.MENU;
            optionMenu.init();
        }
    }
}
