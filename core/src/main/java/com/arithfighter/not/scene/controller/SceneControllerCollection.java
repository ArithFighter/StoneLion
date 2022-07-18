package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.save.OptionSave;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneCollection;
import com.arithfighter.not.scene.scene.*;

public class SceneControllerCollection {
    private final TransitionController transitionController;
    private final StageController stageController;
    private final OptionController optionController;
    private final DeckSelectionController deckSelectionController;
    private final GameOverController gameOverController;
    private final EnchantmentMapController enchantmentMapController;

    public SceneControllerCollection(SceneCollection sceneCollection, OptionSave optionSave) {
        StageDeployer stageDeployer = new StageDeployer();

        transitionController = new TransitionController(sceneCollection);
        transitionController.setStageDeployer(stageDeployer);

        stageController = new StageController(sceneCollection);
        stageController.setStageDeployer(stageDeployer);

        optionController = new OptionController(sceneCollection);
        optionController.setOptionSave(optionSave);

        deckSelectionController = new DeckSelectionController(sceneCollection);
        deckSelectionController.setStageDeployer(stageDeployer);

        gameOverController = new GameOverController(sceneCollection);

        enchantmentMapController = new EnchantmentMapController(sceneCollection);
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

    public EnchantmentMapController getEnchantmentMapController() {
        return enchantmentMapController;
    }
}

class DeckSelectionController extends SceneAccessor implements SceneControllable {
    private StageDeployer stageDeployer;

    public DeckSelectionController(SceneCollection sceneCollection) {
        super(sceneCollection);
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
        DeckSelection deckSelection = getSceneCollection().getDeckSelection();
        Stage stage = getSceneCollection().getStage();

        if (deckSelection.isStartGame()) {
            setGameScene(GameScene.ENCHANTMENT_MAP);
            stage.setDeck(CharacterList.values()[deckSelection.getDeckIndex()]);
            stage.getRemainCardManager().init();
            stageDeployer.init();
            deckSelection.init();
        }
    }
}

class EnchantmentMapController extends SceneAccessor implements SceneControllable{

    public EnchantmentMapController(SceneCollection sceneCollection) {
        super(sceneCollection);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.ENCHANTMENT_MAP);
    }

    @Override
    public void run() {
        EnchantmentMap enchantmentMap = getSceneCollection().getEnchantmentMap();
        if (enchantmentMap.isStart()){
            enchantmentMap.init();
            setGameScene(GameScene.TRANSITION);
        }
    }
}

class TransitionController extends SceneAccessor implements SceneControllable {
    private StageDeployer stageDeployer;

    public TransitionController(SceneCollection sceneCollection) {
        super(sceneCollection);
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
        Transition transition = getSceneCollection().getTransition();
        Stage stage = getSceneCollection().getStage();

        if (transition.isGameStart()) {
            stage.setGameVariation(stageDeployer.getVariation());
            stage.setBoxQuantity(stageDeployer.getQuantity());

            stageDeployer.update();

            transition.init();
            setGameScene(GameScene.STAGE);
        }
    }
}

class StageController extends SceneAccessor implements SceneControllable {
    private StageDeployer stageDeployer;

    public StageController(SceneCollection sceneCollection) {
        super(sceneCollection);
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
        Stage stage = getSceneCollection().getStage();

        if (stage.getPauseMenu().isOpenOption()) {
            setGameScene(GameScene.OPTION);
            getSceneCollection().getOption().setSceneTemp(GameScene.STAGE);
            stage.getPauseMenu().init();
        }
        if (stage.isComplete()) {
            setGameScene(GameScene.TRANSITION);
            stage.init();

            checkFinishedAllStage();
        }
        if (stage.getPauseMenu().isReturnToMainMenu()) {
            setGameScene(GameScene.DECK_SELECTION);
            stage.init();
        }
        if (stage.getRemainCardManager().isNoRemainCard()){
            setGameScene(GameScene.GAME_OVER);
            stage.init();
        }
    }

    private void checkFinishedAllStage() {
        Stage stage = getSceneCollection().getStage();

        if (stageDeployer.isReachFinalStage(5)) {
            setGameScene(GameScene.GAME_OVER);
            stage.init();
        }
    }
}

class GameOverController extends SceneAccessor implements SceneControllable {

    public GameOverController(SceneCollection sceneCollection) {
        super(sceneCollection);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.GAME_OVER);
    }

    @Override
    public void run() {
        GameOver gameOver = getSceneCollection().getGameOver();
        if (gameOver.isQuit()) {
            setGameScene(GameScene.DECK_SELECTION);
            gameOver.init();
        }
    }
}

class OptionController extends SceneAccessor implements SceneControllable, Optionable {
    private OptionSave optionSave;

    public OptionController(SceneCollection sceneCollection) {
        super(sceneCollection);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.OPTION);
    }

    @Override
    public void run() {
        SceneCollection sceneCollection = getSceneCollection();
        Option option = sceneCollection.getOption();

        if (option.isLeaving()) {
            setGameScene(option.getSceneTemp());
            optionSave.saveOption();
            option.init();
        }
    }

    @Override
    public void setOptionSave(OptionSave optionSave) {
        this.optionSave = optionSave;
    }
}