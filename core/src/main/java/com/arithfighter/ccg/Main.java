package com.arithfighter.ccg;

import com.arithfighter.ccg.file.MyAssetProcessor;
import com.arithfighter.ccg.scene.CharacterMenu;
import com.arithfighter.ccg.scene.Game;
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
            if (characterMenu.isPanelButtonActive())
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

        soundManager = new SoundManager(assetProcessor.getSounds());

        musicManager = new MusicManager(assetProcessor.getMusics());

        characterMenu = new CharacterMenu(assetProcessor.getWidgets(), assetProcessor.getPanels());

        game = new Game(
                assetProcessor.getWidgets(),
                assetProcessor.getCards(),
                soundManager
        );

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        soundManager.setVolume(0.6f);

        musicManager.setVolume(0.8f);

        game.setSelectedPlayerToGame(characterMenu.getSelectIndex());

        if (characterMenu.isGameStart()) {
            characterMenu.init();
            gameState = GameState.GAME;
        }
        if (game.isReturnToMenu()) {
            soundManager.playReturnSound();
            game.init();
            gameState = GameState.MENU;
        }

        drawGame();
    }

    private void drawGame() {
        batch.begin();
        switchScene();
        batch.end();
    }

    private void switchScene() {
        switch (gameState) {
            case MENU:
                musicManager.playMenuMusic();
                characterMenu.setBatch(batch);
                characterMenu.draw();
                break;
            case GAME:
                musicManager.playTheme();
                game.setBatch(batch);
                game.setCursorPos(cursorPos);
                game.update();
                game.draw();
                game.drawData(characterMenu.getSelectIndex());
                break;
        }
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