package com.arithfighter.ccg;

import com.arithfighter.ccg.file.CounterAssetProcessor;
import com.arithfighter.ccg.system.CursorPositionAccessor;
import com.arithfighter.ccg.system.MouseAdapter;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private CounterAssetProcessor assetProcessor;
    private MouseAdapter mouseAdapter;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private Game game;

    @Override
    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.load();

        batch = new SpriteBatch();

        cursorPos = new CursorPositionAccessor();

        game = new Game(assetProcessor.getTextures(), assetProcessor.getCards());

        mouseAdapter = new MouseAdapter(game);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetProcessor.update(17);

        cursorPos.update();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        game.update(cursorPos.getX(), cursorPos.getY());

        drawComponent();
    }

    private void drawComponent() {
        batch.begin();
        game.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        game.dispose();
    }
}