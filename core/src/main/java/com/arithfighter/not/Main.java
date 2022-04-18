package com.arithfighter.not;

import com.arithfighter.not.audio.AudioHandler;
import com.arithfighter.not.audio.MusicManager;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.scene.*;
import com.arithfighter.not.widget.Dialog;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private int selectedCharacterIndex = 0;
    private SceneController sceneController;
    private Dialog dialog;

    @Override
    public void create() {
        gameScene = GameScene.MENU;

        assetProcessor = new MyAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        audioHandler = new AudioHandler(assetProcessor.getSounds(), assetProcessor.getMusics());

        sceneBuilder = new SceneBuilder(assetProcessor, audioHandler.getSoundManager());

        sceneBuilder.setBatch(batch);

        sceneBuilder.setCursorPos(cursorPos);

        audioHandler.setOptionMenu(sceneBuilder.getOptionMenu());

        mouseAdapter = new MouseAdapter(sceneBuilder.getMouseEvents());

        sceneController = new SceneController(sceneBuilder);

        Gdx.input.setInputProcessor(mouseAdapter);

        dialog = new Dialog(assetProcessor.getWidgets());
        dialog.setPoint(new Point(250,300));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        audioHandler.setAudioVolume();

        setSelectedCharacter();

        mouseAdapter.setGameScene(gameScene);

        updateScene();

        playBackgroundMusic();

        drawGame();

        //This is for developer, will remove in launched version
        manualReset();
    }

    private void setSelectedCharacter() {
        selectedCharacterIndex = sceneBuilder.getCharacterMenu().getSelectIndex();

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

        sceneBuilder.drawScene(gameScene);

        dialog.draw(batch, "test numbers on table","this is a dialog");

        //show game data for development
        if (gameScene == GameScene.STAGE)
            sceneBuilder.getStage().drawData(selectedCharacterIndex);

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

        dialog.dispose();
    }
}