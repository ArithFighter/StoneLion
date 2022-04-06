package com.arithfighter.ccg;

import com.arithfighter.ccg.audio.MusicManager;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.file.MyAssetProcessor;
import com.arithfighter.ccg.scene.CharacterMenu;
import com.arithfighter.ccg.scene.Game;
import com.arithfighter.ccg.scene.OptionMenu;
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
    private OptionMenu optionMenu;
    private enum GameScene {MENU, GAME, OPTION}
    private GameScene gameScene = GameScene.MENU;
    private SoundManager soundManager;
    private MusicManager musicManager;

    private final InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (gameScene == GameScene.MENU)
                characterMenu.touchDown(cursorPos.getX(), cursorPos.getY());

            if (gameScene == GameScene.OPTION)
                optionMenu.touchDown(cursorPos.getX(), cursorPos.getY());

            game.touchDown(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            characterMenu.touchDragged();

            optionMenu.touchDragged();

            game.touchDragged(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            characterMenu.touchUp();

            optionMenu.touchUp();

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

        characterMenu = new CharacterMenu(
                assetProcessor.getWidgets(),
                assetProcessor.getPanels(),
                soundManager
        );

        game = new Game(
                assetProcessor.getWidgets(),
                assetProcessor.getCards(),
                soundManager
        );

        optionMenu = new OptionMenu(assetProcessor.getWidgets());

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        soundManager.setVolume(optionMenu.getSoundVolume()/10f);

        musicManager.setVolume(optionMenu.getMusicVolume()/8f);

        game.setSelectedPlayerToGame(characterMenu.getSelectIndex());

        controlScene();

        drawGame();
    }

    private void drawGame() {
        batch.begin();
        switchScene();
        batch.end();
    }

    private void controlScene(){
        if (characterMenu.isGameStart()) {
            characterMenu.init();
            gameScene = GameScene.GAME;
        }
        if (characterMenu.isOpenOption()){
            characterMenu.init();
            gameScene = GameScene.OPTION;
        }
        if (optionMenu.isReturnToMainMenu()){
            optionMenu.init();
            gameScene = GameScene.MENU;
        }
        if (game.isReturnToMenu()) {
            soundManager.playReturnSound();
            game.init();
            gameScene = GameScene.MENU;
        }
    }

    private void switchScene() {
        switch (gameScene) {
            case MENU:
                renderMenu();
                break;
            case GAME:
                renderGame();
                break;
            case OPTION:
                optionMenu.update();
                optionMenu.draw(batch);
                break;
        }
    }

    private void renderMenu(){
        musicManager.playMenuMusic();
        characterMenu.setBatch(batch);
        characterMenu.draw();
    }

    private void renderGame(){
        musicManager.playTheme();
        game.setBatch(batch);
        game.setCursorPos(cursorPos);
        game.update();
        game.draw();
        game.drawData(characterMenu.getSelectIndex());
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