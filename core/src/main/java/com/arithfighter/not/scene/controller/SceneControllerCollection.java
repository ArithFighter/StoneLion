package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.game.GameVariation;
import com.arithfighter.not.entity.numberbox.NumberBoxService;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.builder.SceneBuilder;
import com.arithfighter.not.scene.scene.Option;
import com.arithfighter.not.scene.scene.Stage;
import com.arithfighter.not.scene.scene.Transition;
import com.arithfighter.not.system.RandomNumProducer;
import jdk.tools.jaotc.ELFMacroAssembler;

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

            stageConfigurator.update();

            transition.init();
            setGameScene(GameScene.STAGE);
        }
    }
}

class StageConfigurator{
    private int cursor = 0;
    private final RandomNumProducer randomQuantity;

    StageConfigurator(){
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