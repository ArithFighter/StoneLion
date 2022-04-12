package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.ControlNumber;
import com.arithfighter.not.entity.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetScreen implements SceneEvent, MouseEvent{
    private final ControlNumber tokenBet;
    private final SceneControlButton startButton;
    private SpriteBatch batch;
    private CursorPositionAccessor cursorPos;
    private final int initValue = 30;

    public BetScreen(Texture[] textures){
        tokenBet = new ControlNumber(textures);
        tokenBet.setInitValue(initValue);
        tokenBet.setPosition(500,500);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setPosition(1000,150);
    }

    public boolean isStartGame(){
        return startButton.isStart();
    }

    @Override
    public void touchDown() {
        tokenBet.activate(cursorPos.getX(), cursorPos.getY());

        startButton.getButton().activate(cursorPos.getX(), cursorPos.getY());
    }

    @Override
    public void touchDragged() {
        tokenBet.deactivate();

        startButton.getButton().deactivate();
    }

    @Override
    public void touchUp() {
        tokenBet.deactivate();

        startButton.getButton().deactivate();
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
        startButton.init();
    }

    @Override
    public void update() {
        startButton.handleScene();

        tokenBet.update();
    }

    @Override
    public void draw() {
        tokenBet.draw(batch);

        startButton.getButton().draw(batch, "Start");
    }

    @Override
    public void dispose() {
        tokenBet.dispose();
    }
}
