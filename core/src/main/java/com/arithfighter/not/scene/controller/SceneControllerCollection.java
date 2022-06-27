package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.game.GameVariation;
import com.arithfighter.not.entity.numberbox.NumberBoxService;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;
import com.arithfighter.not.scene.scene.Option;
import com.arithfighter.not.scene.scene.Stage;
import com.arithfighter.not.scene.scene.Transition;
import com.arithfighter.not.system.RandomNumProducer;

class SceneControllerCollection {
    private final TransitionController transitionController;
    private final StageController stageController;
    private final OptionController optionController;

    public SceneControllerCollection(SceneBuilder sceneBuilder) {
        transitionController = new TransitionController(sceneBuilder);
        stageController = new StageController(sceneBuilder);
        optionController = new OptionController(sceneBuilder);
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
}

class TransitionController extends BuilderAccessor implements SceneControllable {
    private final StageConfigurator stageConfigurator;

    public TransitionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
        stageConfigurator = new StageConfigurator();
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
            stage.setGameVariation(stageConfigurator.getVariation());
            stage.setBoxQuantity(stageConfigurator.getQuantity());
            transition.init();
            setGameScene(GameScene.STAGE);
        }
    }
}

class StageConfigurator{
    private final RandomNumProducer randomQuantity;
    private final RandomNumProducer randomVariationIndex;

    StageConfigurator(){
        int maxQuantity = new NumberBoxService().getQuantity();
        randomQuantity = new RandomNumProducer(maxQuantity, 1);

        randomVariationIndex = new RandomNumProducer(GameVariation.values().length-1, 0);
    }

    public int getQuantity(){
        return randomQuantity.getRandomNum();
    }

    public GameVariation getVariation(){
        return GameVariation.values()[randomVariationIndex.getRandomNum()];
    }
}

class StageController extends BuilderAccessor implements SceneControllable {

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
            setGameScene(GameScene.TRANSITION);
            stage.init();
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