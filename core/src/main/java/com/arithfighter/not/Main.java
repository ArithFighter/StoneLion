package com.arithfighter.not;

import com.arithfighter.not.audio.AudioHandler;
import com.arithfighter.not.audio.MusicManager;
import com.arithfighter.not.entity.GameDataDisplacer;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private MyAssetProcessor assetProcessor;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private SceneBuilder sceneBuilder;
    private GameScene gameScene;
    private AudioHandler audioHandler;
    private MouseAdapter mouseAdapter;
    private SceneController sceneController;
    private TextureService textureService;
    private GameSave gameSave;
    private GameDataDisplacer gameDataDisplacer;
    private FontService fontService;

    @Override
    public void create() {
        assetProcessor = new MyAssetProcessor();

        assetProcessor.load();

        textureService = new TextureService(assetProcessor);

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        fontService = new FontService();

        audioHandler = new AudioHandler(assetProcessor.getSounds(), assetProcessor.getMusics());

        sceneBuilder = new SceneBuilder(textureService, audioHandler.getSoundManager(), fontService);

        sceneBuilder.setBatch(batch);

        sceneBuilder.setCursorPos(cursorPos);

        audioHandler.setOptionMenu(sceneBuilder.getOptionMenu());

        mouseAdapter = new MouseAdapter(sceneBuilder.getMouseEvents());

        sceneController = new SceneController(sceneBuilder, GameScene.MENU);

        gameSave = new GameSave();
        sceneBuilder.setAudioVolume(gameSave);

        sceneController.setGameSave(gameSave);

        Gdx.input.setInputProcessor(mouseAdapter);

        gameDataDisplacer = new GameDataDisplacer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        if (assetProcessor.getAssetManager().update()){
            updateScene();

            sceneBuilder.setHighScore(gameSave);

            cursorPos.update();

            audioHandler.setAudioVolume();

            setSelectedCharacter();

            mouseAdapter.setGameScene(gameScene);

            playBackgroundMusic();

            drawGame();

            //This is for developer, will remove in launched version
            manualReset();
        }
    }

    private void setSelectedCharacter() {
        int selectedCharacterIndex = sceneBuilder.getCharacterMenu().getSelectIndex();

        sceneBuilder.getStage().setSelectedPlayerToGame(selectedCharacterIndex);
    }

    private void updateScene() {
        sceneController.updateScene();

        gameScene = sceneController.getGameScene();
    }

    private void manualReset() {
        if (gameScene == GameScene.STAGE) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R))
                sceneBuilder.getStage().init();
        }
    }

    private void drawGame() {
        batch.begin();

        sceneBuilder.renderScene(gameScene);

        gameDataDisplacer.setCursorPoint(cursorPos.getX(), cursorPos.getY());
        gameDataDisplacer.draw(batch);

        batch.end();
    }

    public void playBackgroundMusic() {
        MusicManager musicManager = audioHandler.getMusicManager();
        if (gameScene == GameScene.MENU ||
                gameScene == GameScene.OPTION ||
                gameScene == GameScene.BET)
            musicManager.playMenuMusic();

        if (gameScene == GameScene.STAGE)
            musicManager.playTheme();
    }

    @Override
    public void dispose() {
        batch.dispose();

        assetProcessor.dispose();

        sceneBuilder.dispose();

        audioHandler.dispose();

        textureService.dispose();

        fontService.dispose();
    }
}