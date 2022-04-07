package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.CursorPositionAccessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface SceneEvent {
    void setCursorPos(CursorPositionAccessor cursorPos);
    void setBatch(SpriteBatch batch);
    void init();
    void update();
    void draw();
    void dispose();
}
