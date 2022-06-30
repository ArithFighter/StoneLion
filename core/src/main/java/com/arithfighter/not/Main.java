package com.arithfighter.not;

import com.arithfighter.not.audio.AudioHandler;
import com.arithfighter.not.audio.MusicController;
import com.arithfighter.not.entity.GameDataDisplacer;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.save.GameSave;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.OptionEvent;
import com.arithfighter.not.scene.builder.SceneBuilder;
import com.arithfighter.not.scene.controller.SceneController;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import static com.arithfighter.not.scene.GameScene.*;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private MyAssetProcessor assetProcessor;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private SceneBuilder sceneBuilder;
    private AudioHandler audioHandler;
    private MouseAdapter mouseAdapter;
    private SceneController sceneController;
    private FontService fontService;

    @Override
    public void create() {
        assetProcessor = new MyAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        fontService = new FontService();

        audioHandler = new AudioHandler(assetProcessor.getSounds(), assetProcessor.getMusics());

        TextureService textureService = new TextureService(assetProcessor);
        sceneBuilder = new SceneBuilder(textureService, audioHandler.getSoundManager(), fontService);
        sceneBuilder.setBatch(batch);
        sceneBuilder.setCursorPos(cursorPos);
        setGameSave();

        sceneController = new SceneController(sceneBuilder, DECK_SELECTION);

        mouseAdapter = new MouseAdapter(sceneBuilder.getMouseEvents());

        mouseAdapter.setSceneList(getMouseEventScenes());

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    private GameScene[] getMouseEventScenes(){
        List<GameScene> gameSceneList = new ArrayList<>();
        for (GameScene g:GameScene.values()){
            if (g.getSceneModel().getMouseEvent()!=null)
                gameSceneList.add(g);
        }
        GameScene[] mouseEventScenes = new GameScene[gameSceneList.size()];
        for (int i =0;i< mouseEventScenes.length;i++)
            mouseEventScenes[i] = gameSceneList.get(i);

        return mouseEventScenes;
    }

    private void setGameSave() {
        GameSave gameSave = new GameSave();
        sceneBuilder.setOptionSave(gameSave);
        sceneBuilder.getOptionSave().loadSave();
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
        OptionEvent option = sceneBuilder.getOption();
        float soundVolume = option.getSoundVolume();
        float musicVolume = option.getMusicVolume();

        audioHandler.setMusicVolume(musicVolume);
        audioHandler.setSoundVolume(soundVolume);
    }

    private void drawGame(GameScene gameScene) {
        batch.begin();

        SceneDrawer sceneDrawer = new SceneDrawer(sceneBuilder.getSceneEvents());
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