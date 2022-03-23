package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.Player;
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
    private Game game;
    private Player[] players;
    private CharacterMenu characterMenu;

    private enum GameState{MENU, GAME}
    private GameState gameState = GameState.MENU;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            game.getPlayer().activateCard(cursorPos.getX(), cursorPos.getY());

            game.getReturnButton().activate(cursorPos.getX(), cursorPos.getY());

            characterMenu.activateButton(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            game.getPlayer().updateWhenDrag(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            game.getBoardArea().playCardOnBoard(cursorPos.getX(), cursorPos.getY());

            game.getReturnButton().deactivate();

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

        characterMenu = new CharacterMenu(assetProcessor.getTextures());

        players = new Player[characterMenu.getSelectionQuantity()];

        for (int i = 0; i< characterMenu.getSelectionQuantity();i++)
            players[i] = new Player(
                    assetProcessor.getTextures(),
                    assetProcessor.getCards(),
                    CharacterList.values()[i]);

        game = new Game(assetProcessor.getTextures());

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        int selectionIndex = characterMenu.getSelectIndex();

        game.setPlayer(players[selectionIndex]);

        if (characterMenu.isStart()){
            characterMenu.init();
            gameState = GameState.GAME;
        }
        if (game.isReturnToMenu()){
            game.init();
            gameState = GameState.MENU;
        }

        drawGame();
    }

    private void drawGame() {
        batch.begin();
        if (gameState == GameState.MENU){
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

        for (Player player:players)
            player.dispose();

        characterMenu.dispose();
    }
}