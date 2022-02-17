package com.arithfighter.ccg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Camera camera;
    GameCore core;
    @Override
    public void create() {
        camera = new Camera();
        camera.create();

        core = new GameCore();
        core.create();
    }

    @Override
    public void resize(int width, int height) {
        camera.update(width,height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		core.render();
    }

    @Override
    public void dispose() {
        core.dispose();
    }
}