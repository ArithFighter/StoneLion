package com.arithfighter.not;

import com.arithfighter.not.audio.MusicManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.file.MyAssetProcessor;
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

        sceneBuilder = new SceneBuilder(assetProcessor,soundManager);

        sceneBuilder.setBatch(batch);

        sceneBuilder.setCursorPos(cursorPos);

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

        sceneBuilder.getStage().setSelectedPlayerToGame(selectedCharacterIndex);

        mouseAdapter.setGameScene(gameScene);

        controlScene();

        switchMusic();

        drawGame();

        //This is for developer, will remove in launched version
        manualReset();
    }

    private void manualReset() {
        if (gameScene == GameScene.STAGE){
            if (Gdx.input.isKeyJustPressed(Input.Keys.R))
                sceneBuilder.getStage().init();
        }
    }

    private void drawGame() {
        batch.begin();

        drawScene();

        //show game data for development
        if (gameScene == GameScene.STAGE)
            sceneBuilder.getStage().drawData(selectedCharacterIndex);

        batch.end();
    }

    private void controlScene() {
        if (sceneBuilder.getCharacterMenu().isGameStart()) {
            gameScene = GameScene.BET;
            sceneBuilder.getCharacterMenu().init();
        }
        if (sceneBuilder.getCharacterMenu().isOpenOption()) {
            gameScene = GameScene.OPTION;
            sceneBuilder.getCharacterMenu().init();
        }
        if (sceneBuilder.getBetScreen().isStartGame()){
            gameScene = GameScene.STAGE;
            sceneBuilder.getBetScreen().init();
        }
        if (sceneBuilder.getStage().isReturnToMenu()) {
            gameScene = GameScene.MENU;
            sceneBuilder.getStage().init();
        }
        if (sceneBuilder.getOptionMenu().isReturnToMainMenu()) {
            gameScene = GameScene.MENU;
            sceneBuilder.getOptionMenu().init();
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

        if (gameScene == GameScene.STAGE)
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