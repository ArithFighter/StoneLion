package com.arithfighter.ccg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class Camera {
    private ScalingViewport viewport;

    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScalingViewport(Scaling.fit,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }

    public void update(int width, int height) {
        viewport.update(width, height);
    }
}
