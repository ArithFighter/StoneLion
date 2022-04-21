package com.arithfighter.not.scene;

import com.arithfighter.not.pojo.GameRecorder;

public class SceneController {
    private GameScene gameScene;
    private final CharacterMenu characterMenu;
    private final BetScreen betScreen;
    private final Stage stage;
    private final ResultScreen resultScreen;
    private final GameOver gameOver;
    private final OptionMenu optionMenu;
    private final int initTokens = 100;
    private final GameRecorderController gameRecorderController;

    public SceneController(SceneBuilder sceneBuilder) {
        gameScene = GameScene.MENU;

        gameRecorderController = new GameRecorderController();

        characterMenu = sceneBuilder.getCharacterMenu();

        betScreen = sceneBuilder.getBetScreen();

        stage = sceneBuilder.getStage();

        resultScreen = sceneBuilder.getResultScreen();

        gameOver = sceneBuilder.getGameOver();

        optionMenu = sceneBuilder.getOptionMenu();
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        stage.setGameRecorder(gameRecorderController.getGameRecorder());

        switch (gameScene){
            case MENU:
                manageMenu();
                break;
            case BET:
                manageBet();
                break;
            case STAGE:
                manageStage();
                break;
            case RESULT:
                manageResult();
                break;
            case GAME_OVER:
                manageGameOver();
                break;
            case OPTION:
                manageOption();
                break;
        }
    }

    private void manageMenu(){
        if (characterMenu.isGameStart()) {
            gameScene = GameScene.BET;
            gameRecorderController.init();
            stage.setInitTokens(initTokens);
            betScreen.setToken(stage.getTokenHolder().getValue());
            characterMenu.init();
        }
        if (characterMenu.isOpenOption()) {
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.MENU);
            characterMenu.init();
        }
    }

    private void manageOption(){
        if (optionMenu.isLeaving()) {
            gameScene = optionMenu.getSceneTemp();
            optionMenu.init();
        }
    }

    private void manageStage(){
        if (stage.isQuit()) {
            gameScene = GameScene.MENU;
            stage.init();
            stage.setInitTokens(initTokens);
        }
        if (stage.isOpenOption()){
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.STAGE);
            stage.initPauseMenu();
        }
        if (stage.isAllNumZero()){
            gameScene = GameScene.RESULT;
            resultScreen.setState(ResultState.WIN);
            gameRecorderController.updateWhenWin();
            stage.getTokenHolder().increaseValue(betScreen.getBet());
            resultScreen.setRemainingTokens(stage.getTokenHolder().getValue());
            stage.init();
        }
        if (stage.isExceedCardLimit()&& gameScene == GameScene.STAGE){
            gameScene = GameScene.RESULT;
            resultScreen.setState(ResultState.LOOSE);
            gameRecorderController.updateWhenLoose();
            stage.getTokenHolder().decreaseValue(betScreen.getBet());
            resultScreen.setRemainingTokens(stage.getTokenHolder().getValue());
            stage.init();
        }
    }

    private void manageBet(){
        if (betScreen.isStartGame()) {
            gameScene = GameScene.STAGE;
            gameRecorderController.updateBeforeStartStage(stage.getTokenHolder().getValue());
            stage.setNumberBoxQuantity(betScreen.getNumberBoxQuantity());
            stage.setCardLimit(betScreen.getCardLimit());
            betScreen.init();
        }
    }

    private void manageResult(){
        if (resultScreen.isContinue()){
            gameScene = GameScene.BET;
            betScreen.setToken(stage.getTokenHolder().getValue());
            resultScreen.init();
        }
        if (resultScreen.isQuit()){
            gameScene = GameScene.GAME_OVER;
            resultScreen.init();
        }
    }

    private void manageGameOver(){
        if (gameOver.isQuit()){
            gameScene = GameScene.MENU;
            gameOver.init();
            stage.setInitTokens(initTokens);
        }
    }
}

class GameRecorderController{
    private final GameRecorder gameRecorder;

    public GameRecorderController(){
        gameRecorder = new GameRecorder();
    }

    public GameRecorder getGameRecorder() {
        return gameRecorder;
    }

    public void updateBeforeStartStage(int tokens){
        gameRecorder.getTokenRecorder().reset();
        gameRecorder.getTokenRecorder().update(tokens);
        gameRecorder.getStagesRecorder().update(1);
    }

    public void updateWhenWin(){
        gameRecorder.getWinRecorder().update(1);
    }

    public void updateWhenLoose(){
        gameRecorder.getLoseRecorder().update(1);
    }

    public void init(){
        gameRecorder.init();
    }
}