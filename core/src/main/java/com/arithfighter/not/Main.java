package com.arithfighter.not;

import com.arithfighter.not.file.audio.AudioHandler;
import com.arithfighter.not.file.audio.AudioService;
import com.arithfighter.not.file.audio.MusicController;
import com.arithfighter.not.entity.GameDataDisplacer;
import com.arithfighter.not.file.AssetAccessor;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.save.GameSave;
import com.arithfighter.not.save.OptionSave;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.OptionEvent;
import com.arithfighter.not.scene.SceneDrawer;
import com.arithfighter.not.scene.SceneInitializer;
import com.arithfighter.not.scene.builder.EventAccessor;
import com.arithfighter.not.scene.builder.SceneCollection;
import com.arithfighter.not.scene.controller.SceneController;
import com.arithfighter.not.scene.controller.SceneControllerCollection;
import com.arithfighter.not.scene.controller.SceneControllerService;
import com.arithfighter.not.file.texture.TextureService;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.not.scene.GameScene.*;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private MyAssetProcessor assetProcessor;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private AudioHandler audioHandler;
    private MouseAdapter mouseAdapter;
    private SceneController sceneController;
    private FontService fontService;
    private SceneCollection sceneCollection;
    private SceneDrawer sceneDrawer;

    @Override
    public void create() {
        assetProcessor = new MyAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        fontService = new FontService();

        initAudioHandlerAndScene();

        initSceneDrawerAndMouseAdapter();

        Gdx.input.setInputProcessor(mouseAdapter);

        loadGameSaveAndInitSceneController();
    }

    private void initAudioHandlerAndScene(){
        AssetAccessor assetAccessor = new AssetAccessor(assetProcessor.getAssetManager());

        AudioService audioService = new AudioService(assetAccessor);

        TextureService textureService = new TextureService(assetAccessor);

        audioHandler = new AudioHandler(audioService.getSounds(), audioService.getMusic());

        sceneCollection = new SceneCollection(textureService, audioHandler.getSoundManager(), fontService);

        SceneInitializer sceneInitializer = new SceneInitializer(sceneCollection);
        sceneInitializer.run();
    }

    private void initSceneDrawerAndMouseAdapter(){
        EventAccessor eventAccessor = new EventAccessor();
        eventAccessor.setBatch(batch);
        eventAccessor.setCursorPos(cursorPos);

        sceneDrawer = new SceneDrawer(eventAccessor.getSceneEvents());
        mouseAdapter = new MouseAdapter(eventAccessor.getMouseEvents());
    }

    private void loadGameSaveAndInitSceneController(){
        GameSave gameSave = new GameSave();
        OptionSave optionSave = new OptionSave(gameSave, sceneCollection.getOption());
        optionSave.loadSave();

        SceneControllerCollection scc = new SceneControllerCollection(sceneCollection, optionSave);
        SceneControllerService scs = new SceneControllerService(scc);

        sceneController = new SceneController(scs, DECK_SELECTION);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        AssetManager assetManager = assetProcessor.getAssetManager();

        assetManager.update(17);

        if (assetManager.update()) {
            runGame();
        }
    }

    private void runGame() {
        GameScene gameScene = sceneController.getGameScene();

        sceneController.updateScene();

        cursorPos.update();

        mouseAdapter.setGameScene(gameScene);

        setVolume();

        MusicController musicController = new MusicController(audioHandler);
        musicController.setGameScene(gameScene);
        musicController.playBackgroundMusic();

        drawGame(gameScene);
    }

    private void setVolume() {
        OptionEvent option = sceneCollection.getOption();
        float soundVolume = option.getSoundVolume();
        float musicVolume = option.getMusicVolume();

        audioHandler.setMusicVolume(musicVolume);
        audioHandler.setSoundVolume(soundVolume);
    }

    private void drawGame(GameScene gameScene) {
        batch.begin();

        sceneDrawer.draw(gameScene);

        GameDataDisplacer gameDataDisplacer = new GameDataDisplacer(fontService.getFont16());
        gameDataDisplacer.setCursorPoint(cursorPos.getX(), cursorPos.getY());
        gameDataDisplacer.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

        assetProcessor.dispose();

        audioHandler.dispose();

        fontService.dispose();
    }
}