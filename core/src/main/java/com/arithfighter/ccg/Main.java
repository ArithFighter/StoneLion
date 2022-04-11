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
    private int selectedCharacterIndex = 0;

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

        OptionMenu optionMenu = sceneBuilder.getOptionMenu();
        float soundVolume = optionMenu.getSoundVolume() / 10f;
        soundManager.setVolume(soundVolume);

        float musicVolume = optionMenu.getMusicVolume() / 8f;
        musicManager.setVolume(musicVolume);

        selectedCharacterIndex = sceneBuilder.getCharacterMenu().getSelectIndex();

        sceneBuilder.getGame().setSelectedPlayerToGame(selectedCharacterIndex);

        mouseAdapter.setGameScene(gameScene);

        controlScene();

        switchMusic();

        drawGame();
    }

    private void drawGame() {
        batch.begin();

        drawScene();

        //show game data for development
        if (gameScene == GameScene.GAME)
            sceneBuilder.getGame().drawData(selectedCharacterIndex);

        batch.end();
    }

    private void controlScene() {
        boolean[] isChangeScene = {
                sceneBuilder.getCharacterMenu().isGameStart(),
                sceneBuilder.getCharacterMenu().isOpenOption(),
                sceneBuilder.getGame().isReturnToMenu(),
                sceneBuilder.getOptionMenu().isReturnToMainMenu()
        };
        SceneEvent[] sceneEvents = sceneBuilder.getSceneEvents();

        if (isChangeScene[0]) {
            gameScene = GameScene.GAME;
            sceneEvents[0].init();
        }
        if (isChangeScene[1]) {
            gameScene = GameScene.OPTION;
            sceneEvents[0].init();
        }
        if (isChangeScene[2]) {
            gameScene = GameScene.MENU;
            sceneEvents[1].init();
        }
        if (isChangeScene[3]) {
            gameScene = GameScene.MENU;
            sceneEvents[2].init();
        }
    }

    private void drawScene() {
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