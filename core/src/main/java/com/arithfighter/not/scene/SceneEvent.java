package com.arithfighter.not.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface SceneEvent {
    void setBatch(SpriteBatch batch);
    void init();
    void update();
    void draw();
    void dispose();
}
