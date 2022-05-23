package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.GameRecorder;
import com.arithfighter.not.pojo.TokenHolder;
import com.badlogic.gdx.Preferences;

public class SceneManager {
    private final MenuManager menuManager;
    private final ResultManager resultManager;
    private final OptionManager optionManager;
    private GameScene gameScene;
    private final SceneFactory[] sceneFactories;
    private final SceneManageable[] sceneManageable;

    public SceneManager(SceneBuilder sceneBuilder, GameRecorder gameRecorder) {
        TokenHolder tokenHolder = new TokenHolder();

        StageManager stageManager = new StageManager(sceneBuilder);
        stageManager.initScene();
        stageManager.setGameRecorder(gameRecorder);
        stageManager.setTokenHolder(tokenHolder);

        menuManager = new MenuManager(sceneBuilder);
        menuManager.initScene();
        menuManager.setGameRecorder(gameRecorder);
        menuManager.setTokenHolder(tokenHolder);

        BetManager betManager = new BetManager(sceneBuilder);
        betManager.initScene();
        betManager.setGameRecorder(gameRecorder);
        betManager.setTokenHolder(tokenHolder);

        resultManager = new ResultManager(sceneBuilder);
        resultManager.initScene();
        resultManager.setGameRecorder(gameRecorder);
        resultManager.setTokenHolder(tokenHolder);

        optionManager = new OptionManager(sceneBuilder);
        optionManager.initScene();

        GameOverManager gameOverManager = new GameOverManager(sceneBuilder);
        gameOverManager.initScene();

        EndingManager endingManager = new EndingManager(sceneBuilder);
        endingManager.initScene();

        sceneFactories = new SceneFactory[]{
                menuManager,
                betManager,
                stageManager,
                resultManager,
                gameOverManager,
                endingManager,
                optionManager
        };

        sceneManageable = new SceneManageable[]{
                menuManager,
                betManager,
                stageManager,
                resultManager,
                gameOverManager,
                endingManager,
                optionManager
        };
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

    public void update(int index){
        for (SceneManageable s: sceneManageable)
            s.initScene();
        sceneManageable[index].run();
        gameScene = sceneFactories[index].getGamaScene();
    }
}

interface SceneFactory{
    GameScene getGamaScene();
}

interface SceneManageable {
    void initScene();
    void run();
}

class MenuManager extends BuilderAccessor implements SceneManageable{
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

    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void initScene() {
        setGameScene(GameScene.MENU);
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        CharacterMenu characterMenu = sceneBuilder.getCharacterMenu();
        BetScreen betScreen = sceneBuilder.getBetScreen();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (characterMenu.isGameStart()) {
            setGameScene(GameScene.BET);
            gameRecorder.init();
            tokenHolder.reset();
            receiveTokens();
            betScreen.setNumberBoxQuantity();
            betScreen.setYourTokens(tokenHolder.getTokens());
            characterMenu.init();
        }
        if (characterMenu.isOpenOption()) {
            setGameScene(GameScene.OPTION);
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

class BetManager extends BuilderAccessor implements SceneManageable{
    private GameRecorder gameRecorder;
    private TokenHolder tokenHolder;

    public BetManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public void setTokenHolder(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void initScene() {
        setGameScene(GameScene.BET);
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        if (betScreen.isStartGame()) {
            setGameScene(GameScene.STAGE);
            gameRecorder.getTokenRecorder().reset();
            gameRecorder.getTokenRecorder().update(tokenHolder.getTokens());
            gameRecorder.getStagesRecorder().update(1);

            stage.setCardLimit(betScreen.getCardLimit());
        }
    }
}

class StageManager extends BuilderAccessor implements SceneManageable{
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

    public void initScene() {
        setGameScene(GameScene.STAGE);
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();
        ResultScreen resultScreen = sceneBuilder.getResultScreen();
        Stage stage = sceneBuilder.getStage();
        BetScreen betScreen = sceneBuilder.getBetScreen();

        setBoxQuantityList();

        if (stageAction.isQuit()) {
            setGameScene(GameScene.MENU);
            resetStage();
            betScreen.init();
            stage.init();
        }
        if (stageAction.isOpenOption()) {
            setGameScene(GameScene.OPTION);
            optionMenu.setSceneTemp(GameScene.STAGE);
            stage.getPauseMenu().init();
        }
        if (stageAction.isWin()) {
            cursor++;
            resultScreen.setRemainingTokens(tokenHolder.getTokens());
            stage.init();

        }
        if (isAllGameCompleted()) {
            setGameScene(GameScene.RESULT);
            resultScreen.setState(ResultState.WIN);

            tokenHolder.gain(betScreen.getBet());
            gameRecorder.getWinRecorder().update(1);

            resultScreen.setRemainingTokens(tokenHolder.getTokens());
            resetStage();
            stage.init();
        }
        if (stageAction.isLose()) {
            setGameScene(GameScene.RESULT);
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

class ResultManager extends BuilderAccessor implements SceneManageable{
    private GameRecorder gameRecorder;
    private GameSave gameSave;
    private TokenHolder tokenHolder;

    public ResultManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public void initScene() {
        setGameScene(GameScene.RESULT);
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
                setGameScene(GameScene.ENDING);
            else
                setGameScene(GameScene.BET);
            setBetScreen();
            doBeforeLeave();
        }
        if (resultScreen.isQuit()) {
            setGameScene(GameScene.GAME_OVER);
            doBeforeLeave();
        }
    }

    private boolean isEnd(int condition) {
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

class GameOverManager extends BuilderAccessor implements SceneManageable{

    public GameOverManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.GAME_OVER);
    }

    public void run() {
        GameOver gameOver = getSceneBuilder().getGameOver();
        if (gameOver.isQuit()) {
            setGameScene(GameScene.MENU);
            gameOver.init();
        }
    }
}

class EndingManager extends BuilderAccessor implements SceneManageable{

    public EndingManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void initScene() {
        setGameScene(GameScene.ENDING);
    }

    public void run() {
        Ending ending = getSceneBuilder().getEnding();
        if (ending.isLeave()) {
            setGameScene(GameScene.MENU);
            ending.init();
        }
    }
}

class OptionManager extends BuilderAccessor implements SceneManageable{
    private GameSave gameSave;

    public OptionManager(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
    }

    public void initScene() {
        setGameScene(GameScene.OPTION);
    }

    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        OptionMenu optionMenu = sceneBuilder.getOptionMenu();

        if (optionMenu.isLeaving()) {
            setGameScene(optionMenu.getSceneTemp());
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

class BuilderAccessor implements SceneFactory{
    private final SceneBuilder sceneBuilder;
    private GameScene gameScene;

    public BuilderAccessor(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public GameScene getGamaScene() {
        return gameScene;
    }
}