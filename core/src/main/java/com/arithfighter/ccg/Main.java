package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.NumberBoxDisplacer;
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
    private Game[] games;
    private CharacterMenu characterMenu;
    private NumberBoxDisplacer numberBoxDisplacer;
    private int selectionIndex = 0;
    private enum GameState{MENU, GAME}
    private GameState gameState = GameState.MENU;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            games[selectionIndex].getPlayer().activateCard(cursorPos.getX(), cursorPos.getY());

            games[selectionIndex].getReturnButton().activate(cursorPos.getX(), cursorPos.getY());

            characterMenu.activateButton(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            games[selectionIndex].getPlayer().updateWhenDrag(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            games[selectionIndex].getBoardArea().playCardOnBoard(cursorPos.getX(), cursorPos.getY());

            games[selectionIndex].getReturnButton().deactivate();

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

        numberBoxDisplacer = new NumberBoxDisplacer(assetProcessor.getTextures()[3]) {
            @Override
            public void doWhenSumAndNumMatched() {
            }
        };

        games = new Game[characterMenu.getSelectionQuantity()];

        for (int i = 0; i < games.length; i++)
            games[i] = new Game(assetProcessor.getTextures(), assetProcessor.getCards(), CharacterList.values()[i]);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        selectionIndex = characterMenu.getSelectIndex();

        if (characterMenu.isStart()){
            characterMenu.init();
            gameState = GameState.GAME;
        }
        if (games[selectionIndex].isReturnToMenu()){
            games[selectionIndex].init();
            numberBoxDisplacer.refresh();
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
            games[selectionIndex].update(cursorPos.getX(), cursorPos.getY());
            numberBoxDisplacer.update(games[selectionIndex].getPlayer().getSum());
            numberBoxDisplacer.draw(batch);
            games[selectionIndex].draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        for (Game game : games)
            game.dispose();

        numberBoxDisplacer.dispose();
        characterMenu.dispose();
    }
}