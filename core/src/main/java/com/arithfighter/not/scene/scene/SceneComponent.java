package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneComponent {
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;

    public void setCursorPos(CursorPositionAccessor cursorPos) {
        this.cursorPos = cursorPos;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public CursorPositionAccessor getCursorPos() {
        return cursorPos;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
