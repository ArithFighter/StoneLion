package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.game.GameVariation;
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
        transitionController = new TransitionController(sceneCollection);

        stageController = new StageController(sceneCollection);

        optionController = new OptionController(sceneCollection);
        optionController.setOptionSave(optionSave);

        deckSelectionController = new DeckSelectionController(sceneCollection);

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
    public DeckSelectionController(SceneCollection sceneCollection) {
        super(sceneCollection);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.DECK_SELECTION);
    }

    @Override
    public void run() {
        DeckSelection deckSelection = getSceneCollection().getDeckSelection();
        Stage stage = getSceneCollection().getStage();
        EnchantmentMap enchantmentMap = getSceneCollection().getEnchantmentMap();

        if (deckSelection.isStartGame()) {
            setGameScene(GameScene.ENCHANTMENT_MAP);
            stage.init();
            stage.setDeck(CharacterList.values()[deckSelection.getDeckIndex()]);
            stage.getRemainCardManager().init();
            enchantmentMap.init();
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
            setGameScene(GameScene.TRANSITION);
            enchantmentMap.init();
        }
    }
}

class TransitionController extends SceneAccessor implements SceneControllable {
    public TransitionController(SceneCollection sceneCollection) {
        super(sceneCollection);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.TRANSITION);
    }

    @Override
    public void run() {
        Transition transition = getSceneCollection().getTransition();
        Stage stage = getSceneCollection().getStage();
        EnchantmentMap enchantmentMap = getSceneCollection().getEnchantmentMap();

        if (transition.isGameStart()) {
            stage.init();
            stage.setBellQuantity(enchantmentMap.getBellQuantity());
            stage.setGameVariation(GameVariation.STANDARD);
            transition.init();
            setGameScene(GameScene.STAGE);
        }
    }
}

class StageController extends SceneAccessor implements SceneControllable {
    public StageController(SceneCollection sceneCollection) {
        super(sceneCollection);
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
        if (stage.isChangeScene()) {
            setGameScene(GameScene.ENCHANTMENT_MAP);
        }
        if (stage.getPauseMenu().isReturnToMainMenu()) {
            setGameScene(GameScene.DECK_SELECTION);
        }
        if (stage.getRemainCardManager().isNoRemainCard()){
            setGameScene(GameScene.GAME_OVER);
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