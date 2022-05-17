package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;
import com.badlogic.gdx.Preferences;

public class SceneController {
    private GameScene gameScene;
    private final SceneBuilder sceneBuilder;
    private GameSave gameSave;
    private final StageManager stageManager;
    private final GameRecorder gameRecorder;
    private final MenuManager menuManager;
    private final BetManager betManager;
    private final ResultManager resultManager;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        gameRecorder = new GameRecorder();

        this.sceneBuilder = sceneBuilder;

        stageManager = new StageManager(sceneBuilder.getStage());

        menuManager = new MenuManager();
        menuManager.setSceneBuilder(sceneBuilder);
        menuManager.setGameRecorder(gameRecorder);

        betManager = new BetManager();
        betManager.setSceneBuilder(sceneBuilder);
        betManager.setGameRecorder(gameRecorder);

        resultManager = new ResultManager();
        resultManager.setGameRecorder(gameRecorder);
        resultManager.setSceneBuilder(sceneBuilder);
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
        menuManager.setGameSave(gameSave);
        resultManager.setGameSave(gameSave);
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        Stage stage = sceneBuilder.getStage();
        stage.getRecordDisplacer().setGameRecorder(gameRecorder);

        switch (gameScene) {
            case MENU:
                menuManager.run();
                gameScene = menuManager.getGameScene();
                break;
            case BET:
                resultManager.init();
                betManager.run();
                gameScene = betManager.getGameScene();
                break;
            case STAGE:
                manageStage();
                menuManager.init();
                betManager.init();
                break;
            case RESULT:
                resultManager.run();
                gameScene = resultManager.getGameScene();
                break;
            case GAME_OVER:
                manageGameOver();
                break;
            case ENDING:
                Ending ending = sceneBuilder.getEnding();
                if (ending.isLeave()) {
                    gameScene = GameScene.MENU;
                    ending.init();
                }
                break;
            case OPTION:
                manageOption();
                menuManager.init();
                break;
        }
    }

    private void manageOption() {
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (optionMenu.isLeaving()) {
            gameScene = optionMenu.getSceneTemp();
            saveOption();
            optionMenu.init();
        }
    }

    private void saveOption() {
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        Preferences pref = gameSave.getPreferences();
        String soundVolumeKey = gameSave.getOptionKeys()[0];
        String musicVolumeKey = gameSave.getOptionKeys()[1];

        pref.putInteger(soundVolumeKey, optionMenu.getSoundVolume());
        pref.putInteger(musicVolumeKey, optionMenu.getMusicVolume());
        pref.flush();
    }

    private void manageStage() {
        Stage stage = sceneBuilder.getStage();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        if (stageManager.isQuit()) {
            gameScene = GameScene.MENU;
            stage.init();
        }
        if (stageManager.isOpenOption()) {
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.STAGE);
            stage.getPauseMenu().init();
        }
        if (stageManager.isWin() || stageManager.isLose()) {
            gameScene = GameScene.RESULT;

            if (stageManager.isWin())
                doWhenWin();
            if (stageManager.isLose())
                doWhenLoose();

            resultScreen.setRemainingTokens(stage.getTokenHolder().getTokens());
            stage.init();
        }
    }

    private void doWhenWin() {
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        resultScreen.setState(ResultState.WIN);
        stage.getTokenHolder().gain(betScreen.getBet());
        gameRecorder.getWinRecorder().update(1);
    }

    private void doWhenLoose() {
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        resultScreen.setState(ResultState.LOOSE);
        stage.getTokenHolder().lose(betScreen.getBet());
        gameRecorder.getLoseRecorder().update(1);
    }

    private void manageGameOver() {
        GameOver gameOver = sceneBuilder.getGameOver();

        if (gameOver.isQuit()) {
            gameScene = GameScene.MENU;
            gameOver.init();
        }
    }
}

class MenuManager{
    private SceneBuilder sceneBuilder;
    private GameScene gameScene = GameScene.MENU;
    private GameRecorder gameRecorder;
    private GameSave gameSave;

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public void setSceneBuilder(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void init(){
        gameScene = GameScene.MENU;
    }

    public void run() {
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (characterMenu.isGameStart()) {
            gameScene = GameScene.BET;
            gameRecorder.init();
            stage.getTokenHolder().reset();
            receiveTokens();
            betScreen.setNumberBoxQuantity();
            betScreen.setInitToken(stage.getTokenHolder().getTokens());
            characterMenu.init();
        }
        if (characterMenu.isOpenOption()) {
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.MENU);
            characterMenu.init();
        }
    }

    private void receiveTokens() {
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        Stage stage = sceneBuilder.getStage();

        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        int initTokens = 500;
        if (pref.getInteger(keys[characterIndex]) == 0)
            stage.getTokenHolder().gain(initTokens);
        else
            stage.getTokenHolder().gain(pref.getInteger(keys[characterIndex]));
    }
}

class BetManager{
    private SceneBuilder sceneBuilder;
    private GameScene gameScene = GameScene.BET;
    private GameRecorder gameRecorder;

    public void setSceneBuilder(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void init(){
        gameScene = GameScene.BET;
    }

    public void run() {
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        if (betScreen.isStartGame()) {
            gameScene = GameScene.STAGE;
            gameRecorder.getTokenRecorder().reset();
            gameRecorder.getTokenRecorder().update(stage.getTokenHolder().getTokens());
            gameRecorder.getStagesRecorder().update(1);

            stage.setNumberBoxQuantity(betScreen.getNumberBoxQuantity());
            stage.setCardLimit(betScreen.getCardLimit());
            betScreen.setNumberBoxQuantity();
            betScreen.init();
        }
    }
}

class StageManager {
    private final Stage stage;

    public StageManager(Stage stage) {
        this.stage = stage;
    }

    public boolean isWin() {
        return stage.getStageMessage().isWin();
    }

    public boolean isLose() {
        return stage.getStageMessage().isLose();
    }

    public boolean isOpenOption() {
        return stage.getPauseMenu().isOpenOption();
    }

    public boolean isQuit() {
        return stage.getPauseMenu().isReturnToMainMenu();
    }
}

class ResultManager{
    private SceneBuilder sceneBuilder;
    private GameScene gameScene = GameScene.RESULT;
    private GameRecorder gameRecorder;
    private GameSave gameSave;

    public void setSceneBuilder(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public void init(){
        gameScene = GameScene.RESULT;
    }

    public void run() {
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        int totalStages = 6;

        if (resultScreen.isContinue()) {
            if (gameRecorder.getStagesRecorder().getRecord() == totalStages)
                gameScene = GameScene.ENDING;
            else
                gameScene = GameScene.BET;

            betScreen.setInitToken(stage.getTokenHolder().getTokens());
            saveTokens();
            resultScreen.init();
        }
        if (resultScreen.isQuit()) {
            gameScene = GameScene.GAME_OVER;
            saveTokens();
            resultScreen.init();
        }
    }

    private void saveTokens() {
        Stage stage = sceneBuilder.getStage();
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();

        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        pref.putInteger(keys[characterIndex], stage.getTokenHolder().getTokens());
        pref.flush();
    }
}