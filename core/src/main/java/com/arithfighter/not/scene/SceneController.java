package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;
import com.badlogic.gdx.Preferences;

public class SceneController {
    private GameScene gameScene;
    private final CharacterMenu characterMenu;
    private final BetScreen betScreen;
    private final Stage stage;
    private final ResultScreen resultScreen;
    private final GameOver gameOver;
    private final OptionMenu optionMenu;
    private final Ending ending;
    private final int initTokens = 500;
    private GameSave gameSave;
    private final StageManager stageManager;
    private final GameRecorder gameRecorder;

    public SceneController(SceneBuilder sceneBuilder, GameScene initScene) {
        gameScene = initScene;

        gameRecorder = new GameRecorder();

        characterMenu = sceneBuilder.getCharacterMenu();

        betScreen = sceneBuilder.getBetScreen();

        stage = sceneBuilder.getStage();

        resultScreen = sceneBuilder.getResultScreen();

        gameOver = sceneBuilder.getGameOver();

        ending = sceneBuilder.getEnding();

        optionMenu = sceneBuilder.getOptionMenu();

        stageManager = new StageManager(stage);
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void updateScene() {
        stage.getRecordDisplacer().setGameRecorder(gameRecorder);

        switch (gameScene) {
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
            case ENDING:
                if (ending.isLeave()) {
                    gameScene = GameScene.MENU;
                    ending.init();
                }
                break;
            case OPTION:
                manageOption();
                break;
        }
    }

    private void manageMenu() {
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
        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        if (pref.getInteger(keys[characterIndex]) == 0)
            stage.getTokenHolder().gain(initTokens);
        else
            stage.getTokenHolder().gain(pref.getInteger(keys[characterIndex]));
    }

    private void manageOption() {
        if (optionMenu.isLeaving()) {
            gameScene = optionMenu.getSceneTemp();
            saveOption();
            optionMenu.init();
        }
    }

    private void saveOption() {
        Preferences pref = gameSave.getPreferences();
        String soundVolumeKey = gameSave.getOptionKeys()[0];
        String musicVolumeKey = gameSave.getOptionKeys()[1];

        pref.putInteger(soundVolumeKey, optionMenu.getSoundVolume());
        pref.putInteger(musicVolumeKey, optionMenu.getMusicVolume());
        pref.flush();
    }

    private void manageBet() {
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

    private void manageStage() {
        if (stageManager.isQuit()) {
            gameScene = GameScene.MENU;
            stage.init();
            stage.getTokenHolder().gain(initTokens);
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
        resultScreen.setState(ResultState.WIN);
        stage.getTokenHolder().gain(betScreen.getBet());
        gameRecorder.getWinRecorder().update(1);
    }

    private void doWhenLoose() {
        resultScreen.setState(ResultState.LOOSE);
        stage.getTokenHolder().lose(betScreen.getBet());
        gameRecorder.getLoseRecorder().update(1);
    }

    private void manageResult() {
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
        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        pref.putInteger(keys[characterIndex], stage.getTokenHolder().getTokens());
        pref.flush();
    }

    private void manageGameOver() {
        if (gameOver.isQuit()) {
            gameScene = GameScene.MENU;
            gameOver.init();
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