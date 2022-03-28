package com.arithfighter.ccg;

import com.arithfighter.ccg.file.MyAssetProcessor;
import com.arithfighter.ccg.scene.CharacterMenu;
import com.arithfighter.ccg.scene.Game;
import com.arithfighter.ccg.system.CursorPositionAccessor;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private MyAssetProcessor assetProcessor;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private CharacterMenu characterMenu;
    private Game game;
    private enum GameState {MENU, GAME}
    private GameState gameState = GameState.MENU;
    private SoundManager soundManager;
    private MusicManager musicManager;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (gameState == GameState.MENU)
                characterMenu.activateButton(cursorPos.getX(), cursorPos.getY());

            game.touchDown(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            game.touchDragged(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (characterMenu.isCharButtonActive())
                soundManager.playTouchedSound();

            if (characterMenu.isStartButtonActive())
                soundManager.playAcceptSound();

            characterMenu.deactivateButton();

            game.touchUp(cursorPos.getX(), cursorPos.getY());
            return true;
        }
    };

    @Override
    public void create() {
        assetProcessor = new MyAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        game = new Game(assetProcessor.getTextures(), assetProcessor.getCards());

        characterMenu = new CharacterMenu(assetProcessor.getTextures());

        soundManager = new SoundManager(assetProcessor.getSounds());

        musicManager = new MusicManager(assetProcessor.getMusics());

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        soundManager.setVolume(0.8f);

        musicManager.setVolume(0.8f);

        game.setCurrentPlayerToGame(characterMenu.getSelectIndex());

        if (characterMenu.isStart()) {
            characterMenu.init();
            gameState = GameState.GAME;
        }
        if (game.isReturnToMenu()) {
            game.init();
            gameState = GameState.MENU;
        }

        drawGame();
    }

    private void drawGame() {
        batch.begin();
        if (gameState == GameState.MENU) {
            musicManager.playMenuMusic();
            characterMenu.setBatch(batch);
            characterMenu.draw();
        }
        if (gameState == GameState.GAME) {
            musicManager.playTheme();
            game.setBatch(batch);
            game.update(cursorPos.getX(), cursorPos.getY());
            game.draw();
            game.drawData(cursorPos.getX(), cursorPos.getY(), characterMenu.getSelectIndex());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

        assetProcessor.dispose();

        game.dispose();

        characterMenu.dispose();

        soundManager.dispose();

        musicManager.dispose();
    }
}