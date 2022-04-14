package com.arithfighter.not.scene;

public class SceneController {
    private GameScene gameScene;
    private final CharacterMenu characterMenu;
    private final BetScreen betScreen;
    private final Stage stage;
    private final ResultScreen resultScreen;
    private final OptionMenu optionMenu;

    public SceneController(SceneBuilder sceneBuilder) {
        gameScene = GameScene.MENU;

        characterMenu = sceneBuilder.getCharacterMenu();

        betScreen = sceneBuilder.getBetScreen();

        stage = sceneBuilder.getStage();

        resultScreen = sceneBuilder.getResultScreen();

        optionMenu = sceneBuilder.getOptionMenu();
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        manageMenu();

        manageBet();

        manageStage();

        manageResult();

        manageOption();
    }

    private void manageMenu(){
        if (characterMenu.isGameStart()) {
            gameScene = GameScene.BET;
            betScreen.setToken(stage.getTokenHolder().getToken());
            characterMenu.init();
        }
        if (characterMenu.isOpenOption()) {
            gameScene = GameScene.OPTION;
            characterMenu.init();
        }
    }

    private void manageOption(){
        if (optionMenu.isReturnToMainMenu()) {
            gameScene = GameScene.MENU;
            optionMenu.init();
        }
    }

    private void manageStage(){
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
    }

    private void manageBet(){
        if (betScreen.isStartGame()) {
            gameScene = GameScene.STAGE;
            stage.setNumberBoxQuantity(betScreen.getNumberBoxQuantity());
            betScreen.init();
        }
    }

    private void manageResult(){
        if (resultScreen.isContinue()){
            gameScene = GameScene.BET;
            betScreen.setToken(stage.getTokenHolder().getToken());
            resultScreen.init();
        }
    }
}
