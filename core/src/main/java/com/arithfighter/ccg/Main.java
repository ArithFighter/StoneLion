package com.arithfighter.ccg;

import com.arithfighter.ccg.audio.MusicManager;
import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.file.MyAssetProcessor;
import com.arithfighter.ccg.scene.*;
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
    private GameScene gameScene = GameScene.MENU;
    private SoundManager soundManager;
    private MusicManager musicManager;
    private MouseAdapter mouseAdapter;

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
        characterMenu.setCursorPos(cursorPos);
        characterMenu.setBatch(batch);

        game = new Game(
                assetProcessor.getWidgets(),
                assetProcessor.getCards(),
                soundManager
        );
        game.setCursorPos(cursorPos);
        game.setBatch(batch);

        optionMenu = new OptionMenu(assetProcessor.getWidgets(), soundManager);
        optionMenu.setCursorPos(cursorPos);
        optionMenu.setBatch(batch);

        MouseEvent[] mouseEvents = new MouseEvent[]{
                characterMenu,
                game,
                optionMenu
        };

        mouseAdapter = new MouseAdapter(mouseEvents);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        soundManager.setVolume(optionMenu.getSoundVolume() / 10f);

        musicManager.setVolume(optionMenu.getMusicVolume() / 8f);

        game.setSelectedPlayerToGame(characterMenu.getSelectIndex());

        mouseAdapter.setGameScene(gameScene);

        controlScene();

        drawGame();
    }

    private void drawGame() {
        batch.begin();
        switchScene();
        batch.end();
    }

    private void controlScene() {
        if (characterMenu.isGameStart()) {
            characterMenu.init();
            gameScene = GameScene.GAME;
        }
        if (characterMenu.isOpenOption()) {
            characterMenu.init();
            gameScene = GameScene.OPTION;
        }
        if (optionMenu.isReturnToMainMenu()) {
            optionMenu.init();
            gameScene = GameScene.MENU;
        }
        if (game.isReturnToMenu()) {
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
                renderOptionMenu();
                break;
        }
    }

    private void renderOptionMenu() {
        musicManager.playMenuMusic();
        optionMenu.update();
        optionMenu.draw();
    }

    private void renderMenu() {
        musicManager.playMenuMusic();
        characterMenu.update();
        characterMenu.draw();
    }

    private void renderGame() {
        musicManager.playTheme();
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

class MouseAdapter extends InputAdapter {
    private final MouseEvent[] mouseEvents;
    private GameScene gameScene;

    public MouseAdapter(MouseEvent[] mouseEvents) {
        this.mouseEvents = mouseEvents;
    }

    public void setGameScene(GameScene gameScene){
        this.gameScene = gameScene;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i =0; i<GameScene.values().length;i++){
            if (gameScene == GameScene.values()[i])
                mouseEvents[i].touchDown();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (MouseEvent mouseEvent:mouseEvents)
            mouseEvent.touchDragged();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (MouseEvent mouseEvent:mouseEvents)
            mouseEvent.touchUp();
        return true;
    }
}