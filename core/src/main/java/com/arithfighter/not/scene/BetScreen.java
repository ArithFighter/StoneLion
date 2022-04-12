package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.ControlNumber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetScreen implements SceneEvent, MouseEvent{
    private final ControlNumber tokenBet;
    private SpriteBatch batch;
    private CursorPositionAccessor cursorPos;
    private final int initValue = 30;

    public BetScreen(Texture[] textures){
        tokenBet = new ControlNumber(textures);
        tokenBet.setInitValue(initValue);
        tokenBet.setPosition(250,150);
    }

    @Override
    public void touchDown() {
        tokenBet.activate(cursorPos.getX(), cursorPos.getY());
    }

    @Override
    public void touchDragged() {
        tokenBet.deactivate();
    }

    @Override
    public void touchUp() {
        tokenBet.deactivate();
    }

    @Override
    public void setCursorPos(CursorPositionAccessor cursorPos) {
        this.cursorPos = cursorPos;
    }

    @Override
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void init() {
        tokenBet.setInitValue(initValue);
    }

    @Override
    public void update() {
        tokenBet.update();
    }

    @Override
    public void draw() {
        tokenBet.draw(batch);
    }

    @Override
    public void dispose() {
        tokenBet.dispose();
    }
}
