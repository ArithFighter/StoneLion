package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.pojo.TokenHolder;
import com.arithfighter.not.scene.scene.*;
import com.arithfighter.not.scene.scene.stage.Stage;
import com.badlogic.gdx.Preferences;

public class SceneControllerService {
    private GameScene gameScene;
    private final SceneFactory[] sceneFactories;
    private final SceneControllable[] sceneManageable;
    private final Savable[] savable;

    public SceneControllerService(SceneBuilder sceneBuilder) {
        TokenHolder tokenHolder = new TokenHolder();

        SceneControllerCollection scc = new SceneControllerCollection(sceneBuilder);
        scc.getStageController().setTokenHolder(tokenHolder);
        scc.getMenuController().setTokenHolder(tokenHolder);
        scc.getResultController().setTokenHolder(tokenHolder);

        sceneFactories = new SceneFactory[]{
                scc.getMenuController(),
                scc.getBetController(),
                scc.getStageController(),
                scc.getResultController(),
                scc.getGameOverController(),
                scc.getEndingController(),
                scc.getOptionController()
        };

        sceneManageable = new SceneControllable[]{
                scc.getMenuController(),
                scc.getBetController(),
                scc.getStageController(),
                scc.getResultController(),
                scc.getGameOverController(),
                scc.getEndingController(),
                scc.getOptionController()
        };

        for (SceneControllable s : sceneManageable)
            s.initScene();

        savable = new Savable[]{
                scc.getMenuController(),
                scc.getResultController(),
                scc.getOptionController()
        };
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameSave(GameSave gameSave) {
        for (Savable s : savable)
            s.setGameSave(gameSave);
    }

    public void update(int index) {
        for (SceneControllable s : sceneManageable)
            s.initScene();
        sceneManageable[index].run();
        gameScene = sceneFactories[index].getGamaScene();
    }
}

class SceneControllerCollection {
    private final StageController stageController;
    private final MenuController menuController;
    private final BetController betController;
    private final ResultController resultController;
    private final OptionController optionController;
    private final GameOverController gameOverController;
    private final EndingController endingController;

    public SceneControllerCollection(SceneBuilder sceneBuilder) {
        stageController = new StageController(sceneBuilder);
        menuController = new MenuController(sceneBuilder);
        betController = new BetController(sceneBuilder);
        resultController = new ResultController(sceneBuilder);
        optionController = new OptionController(sceneBuilder);
        gameOverController = new GameOverController(sceneBuilder);
        endingController = new EndingController(sceneBuilder);
    }

    public StageController getStageController() {
        return stageController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public BetController getBetController() {
        return betController;
    }

    public ResultController getResultController() {
        return resultController;
    }

    public OptionController getOptionController() {
        return optionController;
    }

    public GameOverController getGameOverController() {
        return gameOverController;
    }

    public EndingController getEndingController() {
        return endingController;
    }
}

interface SceneFactory {
    GameScene getGamaScene();
}

interface SceneControllable {
    void initScene();

    void run();
}

interface Savable {
    void setGameSave(GameSave gameSave);
}

class MenuController extends BuilderAccessor implements SceneControllable, Savable {
    private GameSave gameSave;
    private TokenHolder tokenHolder;

    public MenuController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void setGameSave(GameSave gameSave) {
        this.gameSave = gameSave;
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

class BetController extends BuilderAccessor implements SceneControllable {

    public BetController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
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
            stage.setCardLimit(betScreen.getCardLimit());
        }
    }
}

class StageController extends BuilderAccessor implements SceneControllable {
    int cursor = 0;
    int[] boxQuantityList;
    private final StageAction stageAction;
    private TokenHolder tokenHolder;

    public StageController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);

        stageAction = new StageAction(sceneBuilder.getStage());
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

        boxQuantityList = betScreen.getNumberBoxQuantity();

        setBoxQuantityToStage();

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
            stage.init();
        }
        if (isAllGameCompleted()) {
            setGameScene(GameScene.RESULT);
            resultScreen.setWin();

            tokenHolder.gain(betScreen.getBet());

            resultScreen.setRemainingTokens(tokenHolder.getTokens());
            resetStage();
            stage.init();
        }
        if (stageAction.isLose()) {
            setGameScene(GameScene.RESULT);
            resultScreen.setLose();
            tokenHolder.lose(betScreen.getBet());
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

    private void setBoxQuantityToStage() {
        Stage stage = getSceneBuilder().getStage();

        if (boxQuantityList[cursor] > 0)
            stage.setNumberBoxQuantity(boxQuantityList[cursor]);
        else {
            cursor++;
            stage.init();
        }
    }

    private boolean isAllGameCompleted() {
        return cursor > boxQuantityList.length - 1;
    }

    static class StageAction {
        private final Stage stage;

        public StageAction(Stage stage) {
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
}

class ResultController extends BuilderAccessor implements SceneControllable, Savable {
    private GameSave gameSave;
    private TokenHolder tokenHolder;

    public ResultController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
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

        int maxTokens = 500;

        if (resultScreen.isContinue()) {
            if (isEnd(maxTokens))
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
        return tokenHolder.getTokens() >= condition;
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

        characterMenu.setSavedTokens(
                CharacterList.values()[characterIndex],
                pref.getInteger(gameSave.getTokenKey()[characterIndex])
        );
    }
}

class GameOverController extends BuilderAccessor implements SceneControllable {

    public GameOverController(SceneBuilder sceneBuilder) {
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

class EndingController extends BuilderAccessor implements SceneControllable {

    public EndingController(SceneBuilder sceneBuilder) {
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

class OptionController extends BuilderAccessor implements SceneControllable, Savable {
    private GameSave gameSave;

    public OptionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
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

class BuilderAccessor implements SceneFactory {
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