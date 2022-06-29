package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.game.GameVariation;
import com.arithfighter.not.entity.numberbox.NumberBoxService;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;
import com.arithfighter.not.scene.scene.*;
import com.arithfighter.not.system.RandomNumProducer;

class SceneControllerCollection {
    private final TransitionController transitionController;
    private final StageController stageController;
    private final OptionController optionController;
    private final DeckSelectionController deckSelectionController;
    private final GameOverController gameOverController;

    public SceneControllerCollection(SceneBuilder sceneBuilder) {
        transitionController = new TransitionController(sceneBuilder);
        stageController = new StageController(sceneBuilder);
        optionController = new OptionController(sceneBuilder);
        deckSelectionController = new DeckSelectionController(sceneBuilder);
        gameOverController = new GameOverController(sceneBuilder);
    }

    public TransitionController getTransitionController() {
        return transitionController;
    }

    public StageController getStageController() {
        return stageController;
    }

    public OptionController getOptionController() {
        return optionController;
    }

    public DeckSelectionController getDeckSelectionController() {
        return deckSelectionController;
    }

    public GameOverController getGameOverController() {
        return gameOverController;
    }
}

class DeckSelectionController extends BuilderAccessor implements SceneControllable{

    public DeckSelectionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.DECK_SELECTION);
    }

    @Override
    public void run() {
        DeckSelection deckSelection = getSceneBuilder().getDeckSelection();
        Stage stage = getSceneBuilder().getStage();

        if(deckSelection.isStartGame()){
            setGameScene(GameScene.TRANSITION);
            stage.setDeck(CharacterList.values()[deckSelection.getDeckIndex()]);
            deckSelection.init();
        }
    }
}

class TransitionController extends BuilderAccessor implements SceneControllable {
    private final StageDeployer stageDeployer;

    public TransitionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
        stageDeployer = new StageDeployer();
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.TRANSITION);
    }

    @Override
    public void run() {
        Transition transition = getSceneBuilder().getTransition();
        Stage stage = getSceneBuilder().getStage();

        if (transition.isGameStart()) {
            stage.setGameVariation(stageDeployer.getVariation());
            stage.setBoxQuantity(stageDeployer.getQuantity());

            stageDeployer.update();

            transition.init();
            setGameScene(GameScene.STAGE);
        }
    }
}

class StageDeployer {
    private int cursor = 0;
    private final RandomNumProducer randomQuantity;

    StageDeployer(){
        int maxQuantity = new NumberBoxService().getQuantity();
        randomQuantity = new RandomNumProducer(maxQuantity, 6);
    }

    public void update(){
        cursor++;
    }

    public int getQuantity(){
        return randomQuantity.getRandomNum();
    }

    public GameVariation getVariation(){
        GameVariation gv;

        if (cursor<2)
            gv = GameVariation.STANDARD;
        else if (cursor<3)
            gv = GameVariation.FOG;
        else if (cursor<4)
            gv = GameVariation.TABOO;
        else
            gv = GameVariation.TRANSFORM;

        return gv;
    }
}

class StageController extends BuilderAccessor implements SceneControllable {
    private int stageCount = 0;

    public StageController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.STAGE);
    }

    @Override
    public void run() {
        Stage stage = getSceneBuilder().getStage();

        if (stage.getPauseMenu().isOpenOption()) {
            setGameScene(GameScene.OPTION);
            getSceneBuilder().getOption().setSceneTemp(GameScene.STAGE);
            stage.getPauseMenu().init();
        }
        if (stage.isComplete()) {
            stageCount++;
            setGameScene(GameScene.TRANSITION);
            stage.init();

            if (stageCount>=7){
                setGameScene(GameScene.GAME_OVER);
                stage.init();
            }
        }
        if (stage.getPauseMenu().isReturnToMainMenu()){
            setGameScene(GameScene.DECK_SELECTION);
            stage.init();
        }
    }
}

class GameOverController extends BuilderAccessor implements SceneControllable{

    public GameOverController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.GAME_OVER);
    }

    @Override
    public void run() {
        GameOver gameOver = getSceneBuilder().getGameOver();
        if (gameOver.isQuit()){
            setGameScene(GameScene.DECK_SELECTION);
            gameOver.init();
        }
    }
}

class OptionController extends BuilderAccessor implements SceneControllable {
    public OptionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.OPTION);
    }

    @Override
    public void run() {
        SceneBuilder sceneBuilder = getSceneBuilder();
        Option option = sceneBuilder.getOption();

        if (option.isLeaving()) {
            setGameScene(option.getSceneTemp());
            sceneBuilder.getOptionSave().saveOption();
            option.init();
        }
    }
}