package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;
import com.arithfighter.not.pojo.TokenHolder;
import com.badlogic.gdx.Preferences;

public class SceneManager {
    private final MenuManager menuManager;
    private final BetManager betManager;
    private final StageManager stageManager;
    private final ResultManager resultManager;
    private final OptionManager optionManager;
    private GameScene gameScene;

    public SceneManager(SceneBuilder sceneBuilder, GameRecorder gameRecorder) {
        TokenHolder tokenHolder = new TokenHolder();

        stageManager = new StageManager(sceneBuilder);
        stageManager.setGameRecorder(gameRecorder);
        stageManager.setTokenHolder(tokenHolder);

        menuManager = new MenuManager(sceneBuilder);
        menuManager.setGameRecorder(gameRecorder);
        menuManager.setTokenHolder(tokenHolder);

        betManager = new BetManager(sceneBuilder);
        betManager.setGameRecorder(gameRecorder);
        betManager.setTokenHolder(tokenHolder);

        resultManager = new ResultManager(sceneBuilder);
        resultManager.setGameRecorder(gameRecorder);
        resultManager.setTokenHolder(tokenHolder);

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
    private TokenHolder tokenHolder;

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

    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void initScene() {
        gameScene = GameScene.MENU;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (characterMenu.isGameStart()) {
            gameScene = GameScene.BET;
            gameRecorder.init();
            tokenHolder.reset();
            receiveTokens();
            betScreen.setNumberBoxQuantity();
            betScreen.setYourTokens(tokenHolder.getTokens());
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

        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        int initTokens = 50;
        if (pref.getInteger(keys[characterIndex]) <= 0)
            tokenHolder.gain(initTokens);
        else
            tokenHolder.gain(pref.getInteger(keys[characterIndex]));
    }
}

class BetManager extends BuilderService {
    private GameScene gameScene = GameScene.BET;
    private GameRecorder gameRecorder;
    private TokenHolder tokenHolder;

    public BetManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
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
            gameRecorder.getTokenRecorder().update(tokenHolder.getTokens());
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
    private final StageAction stageAction;
    private TokenHolder tokenHolder;

    public StageManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);

        stageAction = new StageAction(sceneBuilder);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public GameScene getGameScene() {
        return gameScene;
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

        if (stageAction.isQuit()) {
            gameScene = GameScene.MENU;
            resetStage();
            betScreen.init();
            stage.init();
        }
        if (stageAction.isOpenOption()) {
            gameScene = GameScene.OPTION;
            optionMenu.setSceneTemp(GameScene.STAGE);
            stage.getPauseMenu().init();
        }
        if (stageAction.isWin()) {
            cursor++;
            resultScreen.setRemainingTokens(tokenHolder.getTokens());
            stage.init();

        }
        if (isAllGameCompleted()) {
            gameScene = GameScene.RESULT;
            resultScreen.setState(ResultState.WIN);

            tokenHolder.gain(betScreen.getBet());
            gameRecorder.getWinRecorder().update(1);

            resultScreen.setRemainingTokens(tokenHolder.getTokens());
            resetStage();
            stage.init();
        }
        if (stageAction.isLose()) {
            gameScene = GameScene.RESULT;
            resultScreen.setState(ResultState.LOOSE);
            tokenHolder.lose(betScreen.getBet());
            gameRecorder.getLoseRecorder().update(1);
            resetStage();
            resultScreen.setRemainingTokens(tokenHolder.getTokens());
            stage.init();
        }
    }

    private void resetStage() {
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

class StageAction {
    private final Stage stage;

    public StageAction(SceneBuilder sceneBuilder) {
        this.stage = sceneBuilder.getStage();
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

class ResultManager extends BuilderService {
    private GameScene gameScene = GameScene.RESULT;
    private GameRecorder gameRecorder;
    private GameSave gameSave;
    private TokenHolder tokenHolder;

    public ResultManager(SceneBuilder sceneBuilder) {
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

    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();

        int totalStages = 6;

        if (resultScreen.isContinue()) {
            if (isEnd(totalStages))
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

    private boolean isEnd(int condition){
        return gameRecorder.getStagesRecorder().getRecord() == condition;
    }

    private void setBetScreen() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        betScreen.setNumberBoxQuantity();
        betScreen.setYourTokens(tokenHolder.getTokens());
    }

    private void doBeforeLeave() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        saveTokens();
        betScreen.init();
        resultScreen.init();
    }

    private void saveTokens() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();

        Preferences pref = gameSave.getPreferences();
        String[] keys = gameSave.getTokenKey();
        int characterIndex = characterMenu.getSelectIndex();

        if (tokenHolder.getTokens() >= 0) {
            pref.putInteger(keys[characterIndex], tokenHolder.getTokens());
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

    public BuilderService(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }
}