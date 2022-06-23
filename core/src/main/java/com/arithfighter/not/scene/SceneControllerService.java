package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.pojo.TokenHolder;
import com.arithfighter.not.scene.scene.Transition;

public class SceneControllerService {
    private GameScene gameScene;
    private final SceneFactory[] sceneFactories;
    private final SceneControllable[] sceneManageable;
    private final Savable[] savable;

    public SceneControllerService(SceneBuilder sceneBuilder) {
        TokenHolder tokenHolder = new TokenHolder();

        SceneControllerCollection scc = new SceneControllerCollection(sceneBuilder);

        sceneFactories = new SceneFactory[]{
                scc.getTransitionController()
        };

        sceneManageable = new SceneControllable[]{
                scc.getTransitionController()
        };

        for (SceneControllable s : sceneManageable)
            s.initScene();

        savable = new Savable[]{

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
    private final TransitionController transitionController;

    public SceneControllerCollection(SceneBuilder sceneBuilder) {
        transitionController = new TransitionController(sceneBuilder);
    }

    public TransitionController getTransitionController() {
        return transitionController;
    }
}

interface SceneControllable {
    void initScene();

    void run();
}

interface Savable {
    void setGameSave(GameSave gameSave);
}

class TransitionController extends BuilderAccessor implements SceneControllable{

    public TransitionController(SceneBuilder sceneBuilder) {
        super(sceneBuilder);
    }

    @Override
    public void initScene() {
        setGameScene(GameScene.TRANSITION);
    }

    @Override
    public void run() {
        Transition transition = getSceneBuilder().getTransition();
        if (transition.isGameStart()){
            setGameScene(GameScene.STAGE);
            transition.init();
        }
    }
}

interface SceneFactory {
    GameScene getGamaScene();
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