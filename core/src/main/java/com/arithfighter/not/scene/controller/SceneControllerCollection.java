package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;
import com.arithfighter.not.scene.scene.*;

class SceneControllerCollection {
    private final TransitionController transitionController;
    private final StageController stageController;
    private final OptionController optionController;
    private final DeckSelectionController deckSelectionController;
    private final GameOverController gameOverController;

    public SceneControllerCollection(SceneBuilder sceneBuilder) {
        StageDeployer stageDeployer = new StageDeployer();

        transitionController = new TransitionController(sceneBuilder);
        transitionController.setStageDeployer(stageDeployer);

        stageController = new StageController(sceneBuilder);
        stageController.setStageDeployer(stageDeployer);

        optionController = new OptionController(sceneBuilder);

        deckSelectionController = new DeckSelectionController(sceneBuilder);
        deckSelectionController.setStageDeployer(stageDeployer);

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

class DeckSelectionController extends BuilderAccessor implements SceneControllable {
    private StageDeployer stageDeployer;

    public DeckSelectionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setStageDeployer(StageDeployer stageDeployer) {
        this.stageDeployer = stageDeployer;
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.DECK_SELECTION);
    }

    @Override
    public void run() {
        DeckSelection deckSelection = getSceneBuilder().getDeckSelection();
        Stage stage = getSceneBuilder().getStage();

        if (deckSelection.isStartGame()) {
            setGameScene(GameScene.TRANSITION);
            stage.setDeck(CharacterList.values()[deckSelection.getDeckIndex()]);
            stageDeployer.init();
            deckSelection.init();
        }
    }
}

class TransitionController extends BuilderAccessor implements SceneControllable {
    private StageDeployer stageDeployer;

    public TransitionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.TRANSITION);
    }

    public void setStageDeployer(StageDeployer stageDeployer) {
        this.stageDeployer = stageDeployer;
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

class StageController extends BuilderAccessor implements SceneControllable {
    private StageDeployer stageDeployer;

    public StageController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    public void setStageDeployer(StageDeployer stageDeployer) {
        this.stageDeployer = stageDeployer;
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
            setGameScene(GameScene.TRANSITION);
            stage.init();

            checkFinishedAllStage();
        }
        if (stage.getPauseMenu().isReturnToMainMenu() ||
                stage.getGamePlayComponent().getRemainCardManager().isNoRemainCard()) {
            goToDeckSelection();
        }
    }

    private void goToDeckSelection() {
        Stage stage = getSceneBuilder().getStage();

        setGameScene(GameScene.DECK_SELECTION);
        stage.init();
    }

    private void checkFinishedAllStage() {
        Stage stage = getSceneBuilder().getStage();

        if (stageDeployer.isReachFinalStage(10)) {
            setGameScene(GameScene.GAME_OVER);
            stage.init();
        }
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

    @Override
    public void run() {
        GameOver gameOver = getSceneBuilder().getGameOver();
        if (gameOver.isQuit()) {
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