package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ResultScreen implements SceneEvent, MouseEvent{
    private final Font winOrLost;
    private final SceneControlButton continueButton;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;

    public ResultScreen(Texture[] textures){
        continueButton = new SceneControlButton(textures[6], 2);
        continueButton.getButton().setPosition(600,150);

        winOrLost = new Font(40);
        winOrLost.setColor(Color.WHITE);
    }

    @Override
    public void touchDown() {
        continueButton.getButton().activate(cursorPos.getX(),cursorPos.getY());
    }

    @Override
    public void touchDragged() {
        continueButton.getButton().deactivate();
    }

    @Override
    public void touchUp() {
        continueButton.getButton().deactivate();
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
        continueButton.init();
    }

    @Override
    public void update() {
        continueButton.update();
    }

    public boolean isContinue(){
        return continueButton.isStart();
    }

    @Override
    public void draw() {
        winOrLost.draw(batch, "You win", 600,500);

        continueButton.getButton().draw(batch, "continue");
    }

    @Override
    public void dispose() {
        winOrLost.dispose();
        continueButton.dispose();
    }
}
