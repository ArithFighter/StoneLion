package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.GameDataAccessor;
import com.arithfighter.ccg.entity.NumberBoxDisplacer;
import com.arithfighter.ccg.file.CounterAssetProcessor;
import com.arithfighter.ccg.system.CursorPositionAccessor;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private GameDataAccessor dataAccessor;

    private enum GameState{MENU, GAME}
    private GameState gameState = GameState.MENU;
    private Game currentGame;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            currentGame.getPlayer().activateCard(cursorPos.getX(), cursorPos.getY());

            currentGame.getReturnButton().activate(cursorPos.getX(), cursorPos.getY());

            characterMenu.activateButton(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            currentGame.getPlayer().updateWhenDrag(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            currentGame.getBoardArea().playCardOnBoard(cursorPos.getX(), cursorPos.getY());

            currentGame.getReturnButton().deactivate();

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
        
        dataAccessor = new GameDataAccessor();

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

        int selectionIndex = characterMenu.getSelectIndex();
        
        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            refresh();
        }
        
        currentGame = games[selectionIndex];

        if (characterMenu.isStart()){
            characterMenu.init();
            gameState = GameState.GAME;
        }
        if (currentGame.isReturnToMenu()){
            refresh();
            gameState = GameState.MENU;
        }

        drawGame();
    }
    
    private void refresh(){
        currentGame.init();
        dataAccessor.resetRecorder();
        numberBoxDisplacer.refresh();
    }

    private void drawGame() {
        batch.begin();
        if (gameState == GameState.MENU){
            characterMenu.draw(batch);
        }
        if (gameState == GameState.GAME) {
            numberBoxDisplacer.update(currentGame.getPlayer().getSum());
            numberBoxDisplacer.draw(batch);
            
            currentGame.update(cursorPos.getX(), cursorPos.getY());
            currentGame.draw(batch);
            
            dataAccessor.draw(cursorPos.getX(), cursorPos.getY(), currentGame.getPlayer().getEnergy(), batch);//for dev
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
        dataAccessor.dispose();
        characterMenu.dispose();
    }
}