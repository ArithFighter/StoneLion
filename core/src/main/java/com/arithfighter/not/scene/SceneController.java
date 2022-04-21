package com.arithfighter.not.scene;

import com.arithfighter.GameSave;
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
    private final GameRecordManager gameRecordManager;
    private final GameSave gameSave;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene, GameSave gameSave) {
        this.gameSave = gameSave;

        gameScene = initScene;

        gameRecordManager = new GameRecordManager();

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
        stage.setGameRecorder(gameRecordManager.getGameRecorder());

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
            gameRecordManager.init();
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
            gameSave.getPreferences().putInteger(gameSave.getOptionKeys()[0], optionMenu.getSoundVolume());
            gameSave.getPreferences().putInteger(gameSave.getOptionKeys()[1], optionMenu.getMusicVolume());
            gameSave.getPreferences().flush();
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
        if (stage.isAllNumZero()||stage.isExceedCardLimit()){
            gameScene = GameScene.RESULT;

            if (stage.isAllNumZero())
                doWhenWin();
            if (stage.isExceedCardLimit())
                doWhenLoose();

            resultScreen.setRemainingTokens(stage.getTokenHolder().getValue());
            stage.init();
        }
    }

    private void doWhenWin(){
        resultScreen.setState(ResultState.WIN);
        stage.getTokenHolder().increaseValue(betScreen.getBet());
        gameRecordManager.updateWhenWin();
    }

    private void doWhenLoose(){
        resultScreen.setState(ResultState.LOOSE);
        stage.getTokenHolder().decreaseValue(betScreen.getBet());
        gameRecordManager.updateWhenLoose();
    }

    private void manageBet(){
        if (betScreen.isStartGame()) {
            gameScene = GameScene.STAGE;
            gameRecordManager.updateBeforeStartStage(stage.getTokenHolder().getValue());
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

class GameRecordManager {
    private final GameRecorder gameRecorder;

    public GameRecordManager(){
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