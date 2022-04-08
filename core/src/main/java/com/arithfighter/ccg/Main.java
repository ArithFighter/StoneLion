package com.arithfighter.ccg;

import com.arithfighter.ccg.audio.MusicManager;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.file.MyAssetProcessor;
import com.arithfighter.ccg.scene.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
    private GameScene gameScene = GameScene.MENU;
    private SoundManager soundManager;
    private MusicManager musicManager;
    private MouseAdapter mouseAdapter;

    @Override
    public void create() {
        assetProcessor = new MyAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        soundManager = new SoundManager(assetProcessor.getSounds());

        musicManager = new MusicManager(assetProcessor.getMusics());

        sceneBuilder = new SceneBuilder(assetProcessor,soundManager,batch,cursorPos);

        mouseAdapter = new MouseAdapter(sceneBuilder.getMouseEvents());

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        soundManager.setVolume(sceneBuilder.getOptionMenu().getSoundVolume() / 10f);

        musicManager.setVolume(sceneBuilder.getOptionMenu().getMusicVolume() / 8f);

        sceneBuilder.getGame().setSelectedPlayerToGame(sceneBuilder.getCharacterMenu().getSelectIndex());

        mouseAdapter.setGameScene(gameScene);

        controlScene();

        switchMusic();

        drawGame();

        //show game data for development
        if (gameScene == GameScene.GAME)
            sceneBuilder.getGame().drawData(sceneBuilder.getCharacterMenu().getSelectIndex());
    }

    private void drawGame() {
        batch.begin();
        switchScene();
        batch.end();
    }

    private void controlScene() {
        if (sceneBuilder.getCharacterMenu().isGameStart()) {
            sceneBuilder.getSceneEvents()[0].init();
            gameScene = GameScene.GAME;
        }
        if (sceneBuilder.getCharacterMenu().isOpenOption()) {
            sceneBuilder.getSceneEvents()[0].init();
            gameScene = GameScene.OPTION;
        }
        if (sceneBuilder.getOptionMenu().isReturnToMainMenu()) {
            sceneBuilder.getSceneEvents()[2].init();
            gameScene = GameScene.MENU;
        }
        if (sceneBuilder.getGame().isReturnToMenu()) {
            sceneBuilder.getSceneEvents()[1].init();
            gameScene = GameScene.MENU;
        }
    }

    private void switchScene() {
        for (int i = 0;i<GameScene.values().length;i++){
            if (gameScene == GameScene.values()[i])
                sceneBuilder.renderScene(i);
        }
    }

    public void switchMusic(){
        if (gameScene == GameScene.MENU || gameScene == GameScene.OPTION)
            musicManager.playMenuMusic();

        if (gameScene == GameScene.GAME)
            musicManager.playTheme();
    }

    @Override
    public void dispose() {
        batch.dispose();

        assetProcessor.dispose();

        sceneBuilder.dispose();

        soundManager.dispose();

        musicManager.dispose();
    }
}