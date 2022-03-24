package com.arithfighter.ccg;

import com.arithfighter.ccg.file.CounterAssetProcessor;
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
    private CounterAssetProcessor assetProcessor;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private CharacterMenu characterMenu;
    private Game game;

    private enum GameState {MENU, GAME}

    private GameState gameState = GameState.MENU;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            game.touchDown(cursorPos.getX(), cursorPos.getY());
            characterMenu.activateButton(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            game.touchDragged(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            game.touchUp(cursorPos.getX(), cursorPos.getY());

            characterMenu.deactivateButton();
            return true;
        }
    };

    @Override
    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        game = new Game(assetProcessor.getTextures(), assetProcessor.getCards());

        characterMenu = new CharacterMenu(assetProcessor.getTextures());

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        int selectionIndex = characterMenu.getSelectIndex();

        game.setCurrentPlayerInGame(selectionIndex);

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
            characterMenu.draw(batch);
        }
        if (gameState == GameState.GAME) {
            game.update(cursorPos.getX(), cursorPos.getY());
            game.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

        assetProcessor.dispose();

        game.dispose();

        characterMenu.dispose();
    }
}