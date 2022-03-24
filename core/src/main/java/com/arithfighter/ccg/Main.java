package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.GameDataAccessor;
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
    private GameComponent gameComponent;
    private Player[] players;
    private CharacterMenu characterMenu;
    private GameDataAccessor dataAccessor;

    private enum GameState {MENU, GAME}

    private GameState gameState = GameState.MENU;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            gameComponent.getPlayer().activateCard(cursorPos.getX(), cursorPos.getY());

            gameComponent.getReturnButton().activate(cursorPos.getX(), cursorPos.getY());

            characterMenu.activateButton(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            gameComponent.getPlayer().updateWhenDrag(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            gameComponent.getBoardArea().playCardOnBoard(cursorPos.getX(), cursorPos.getY());

            gameComponent.getReturnButton().deactivate();

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

        players = new Player[characterMenu.getSelectionQuantity()];

        addPlayers();

        gameComponent = new GameComponent(assetProcessor.getTextures(), dataAccessor);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    private void addPlayers(){
        SkillHandler skillHandler = new SkillHandler();

        for (int i = 0; i < characterMenu.getSelectionQuantity(); i++)
            players[i] = new Player(
                    assetProcessor.getTextures(),
                    assetProcessor.getCards(),
                    CharacterList.values()[i]) {
                @Override
                public void doWhenCardPlayed() {
                    dataAccessor.updatePlayTimes();
                }

                @Override
                public void castSkill(CharacterList character) {
                    skillHandler.cast(character, gameComponent.getNumberBoxDisplacer());
                }
            };
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        int selectionIndex = characterMenu.getSelectIndex();

        gameComponent.setPlayer(players[selectionIndex]);

        if (characterMenu.isStart()) {
            characterMenu.init();
            gameState = GameState.GAME;
        }
        if (gameComponent.isReturnToMenu()) {
            gameComponent.init();
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
            gameComponent.update(cursorPos.getX(), cursorPos.getY());
            gameComponent.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

        assetProcessor.dispose();

        gameComponent.dispose();

        for (Player player : players) player.dispose();

        characterMenu.dispose();
    }
}