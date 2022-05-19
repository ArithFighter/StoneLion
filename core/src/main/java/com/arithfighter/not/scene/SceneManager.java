package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;
import com.badlogic.gdx.Preferences;

public class SceneManager {
    private final MenuManager menuManager;
    private final BetManager betManager;
    private final StageManager stageManager;
    private final ResultManager resultManager;
    private final OptionManager optionManager;
    private GameScene gameScene;

    public SceneManager(SceneBuilder sceneBuilder, GameRecorder gameRecorder) {
        stageManager = new StageManager(sceneBuilder);
        stageManager.setGameRecorder(gameRecorder);

        menuManager = new MenuManager(sceneBuilder);
        menuManager.setGameRecorder(gameRecorder);

        betManager = new BetManager(sceneBuilder);
        betManager.setGameRecorder(gameRecorder);

        resultManager = new ResultManager(sceneBuilder);
        resultManager.setGameRecorder(gameRecorder);

        optionManager = new OptionManager(sceneBuilder);
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameSave(GameSave gameSave) {
        menuManager.setGameSave(gameSave);
        resultManager.setGameSave(gameSave);
        optionManager.setGameSave(gameSave);
    }

    public void manageMenu() {
        optionManager.initScene();
        menuManager.run();
        gameScene = menuManager.getGameScene();
    }

    public void manageBet() {
        stageManager.initScene();
        resultManager.initScene();
        betManager.run();
        gameScene = betManager.getGameScene();
    }

    public void manageStage() {
        optionManager.initScene();
        stageManager.run();
        gameScene = stageManager.getGameScene();
        menuManager.initScene();
        betManager.initScene();
    }

    public void manageResult() {
        stageManager.initScene();
        resultManager.run();
        gameScene = resultManager.getGameScene();
    }

    public void manageOption() {
        stageManager.initScene();
        optionManager.run();
        gameScene = optionManager.getGameScene();
        menuManager.initScene();
    }
}

class MenuManager extends BuilderService {
    private GameScene gameScene = GameScene.MENU;
    private GameRecorder gameRecorder;
    private GameSave gameSave;

    public MenuManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void initScene() {
        gameScene = GameScene.MENU;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
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
            betScreen.setYourTokens(stage.getTokenHolder().getTokens());
            characterMenu.init();
        }
        if (characterMenu.isOpenOption()) {
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.MENU);
            characterMenu.init();
        }
    }

    private void receiveTokens() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        Stage stage = sceneBuilder.getStage();

        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        int initTokens = 50;
        if (pref.getInteger(keys[characterIndex]) <= 0)
            stage.getTokenHolder().gain(initTokens);
        else
            stage.getTokenHolder().gain(pref.getInteger(keys[characterIndex]));
    }
}

class BetManager extends BuilderService {
    private GameScene gameScene = GameScene.BET;
    private GameRecorder gameRecorder;

    public BetManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void initScene() {
        gameScene = GameScene.BET;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        if (betScreen.isStartGame()) {
            gameScene = GameScene.STAGE;
            gameRecorder.getTokenRecorder().reset();
            gameRecorder.getTokenRecorder().update(stage.getTokenHolder().getTokens());
            gameRecorder.getStagesRecorder().update(1);

            stage.setCardLimit(betScreen.getCardLimit());
        }
    }
}

class StageManager extends BuilderService {
    private GameScene gameScene = GameScene.STAGE;
    private GameRecorder gameRecorder;
    int cursor = 0;
    int[] boxQuantityList;

    public StageManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    private boolean isWin() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        return stage.getStageMessage().isWin();
    }

    private boolean isLose() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        return stage.getStageMessage().isLose();
    }

    private boolean isOpenOption() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        return stage.getPauseMenu().isOpenOption();
    }

    private boolean isQuit() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        return stage.getPauseMenu().isReturnToMainMenu();
    }

    public void initScene() {
        gameScene = GameScene.STAGE;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        setBoxQuantityList();

        if (isQuit()) {
            gameScene = GameScene.MENU;
            resetStage();
            betScreen.init();
            stage.init();
        }
        if (isOpenOption()) {
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.STAGE);
            stage.getPauseMenu().init();
        }
        if (isWin()) {
            cursor++;
            resultScreen.setRemainingTokens(stage.getTokenHolder().getTokens());
            stage.init();

            if (isAllGameCompleted()) {
                gameScene = GameScene.RESULT;
                resultScreen.setState(ResultState.WIN);

                stage.getTokenHolder().gain(betScreen.getBet());
                gameRecorder.getWinRecorder().update(1);

                resultScreen.setRemainingTokens(stage.getTokenHolder().getTokens());
                resetStage();
                stage.init();
            }
        }
        if (isLose()) {
            gameScene = GameScene.RESULT;

            resultScreen.setState(ResultState.LOOSE);
            stage.getTokenHolder().lose(betScreen.getBet());
            gameRecorder.getLoseRecorder().update(1);

            resetStage();

            resultScreen.setRemainingTokens(stage.getTokenHolder().getTokens());
            stage.init();
        }
    }

    private void resetStage(){
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();

        cursor = 0;
        stage.getCardLimitManager().getPlayRecord().reset();
    }

    private void setBoxQuantityList() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        boxQuantityList = betScreen.getNumberBoxQuantity();
        stage.setNumberBoxQuantity(boxQuantityList[cursor]);
    }

    private boolean isAllGameCompleted() {
        return cursor > boxQuantityList.length - 1;
    }
}

class ResultManager extends BuilderService {
    private GameScene gameScene = GameScene.RESULT;
    private GameRecorder gameRecorder;
    private GameSave gameSave;

    public ResultManager(SceneBuilder sceneBuilder){
        super(sceneBuilder);
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

    public void initScene() {
        gameScene = GameScene.RESULT;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        int totalStages = 6;

        if (resultScreen.isContinue()) {
            if (gameRecorder.getStagesRecorder().getRecord() == totalStages)
                gameScene = GameScene.ENDING;
            else
                gameScene = GameScene.BET;
            setBetScreen();
            doBeforeLeave();
        }
        if (resultScreen.isQuit()) {
            gameScene = GameScene.GAME_OVER;
            doBeforeLeave();
        }
    }

    private void setBetScreen(){
        SceneBuilder sceneBuilder = getSceneBuilder();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        Stage stage = sceneBuilder.getStage();

        betScreen.setNumberBoxQuantity();
        betScreen.setYourTokens(stage.getTokenHolder().getTokens());
    }

    private void doBeforeLeave(){
        SceneBuilder sceneBuilder = getSceneBuilder();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        saveTokens();
        betScreen.init();
        resultScreen.init();
    }

    private void saveTokens() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();

        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        if (stage.getTokenHolder().getTokens() >= 0) {
            pref.putInteger(keys[characterIndex], stage.getTokenHolder().getTokens());
            pref.flush();
        }
    }
}

class OptionManager extends BuilderService {
    private GameScene gameScene = GameScene.OPTION;
    private GameSave gameSave;

    public OptionManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void initScene() {
        gameScene = GameScene.OPTION;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (optionMenu.isLeaving()) {
            gameScene = optionMenu.getSceneTemp();
            saveOption();
            optionMenu.init();
        }
    }

    private void saveOption() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        Preferences pref = gameSave.getPreferences();
        String soundVolumeKey = gameSave.getOptionKeys()[0];
        String musicVolumeKey = gameSave.getOptionKeys()[1];

        pref.putInteger(soundVolumeKey, optionMenu.getSoundVolume());
        pref.putInteger(musicVolumeKey, optionMenu.getMusicVolume());
        pref.flush();
    }
}

class BuilderService {
    private final SceneBuilder sceneBuilder;

    public BuilderService(SceneBuilder sceneBuilder){
        this.sceneBuilder = sceneBuilder;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }
}