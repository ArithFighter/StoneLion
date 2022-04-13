package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Result implements SceneEvent, MouseEvent{
    private final Font winOrLost;
    private final SceneControlButton continueButton;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;

    public Result(Texture[] textures){
        continueButton = new SceneControlButton(textures[6], 2);

        winOrLost = new Font(40);
        winOrLost.setColor(Color.WHITE);
    }

    @Override
    public void touchDown() {

    }

    @Override
    public void touchDragged() {

    }

    @Override
    public void touchUp() {

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

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        winOrLost.draw(batch, "You win", 600,450);

        continueButton.getButton().draw(batch, "continue");
    }

    @Override
    public void dispose() {
        winOrLost.dispose();
        continueButton.dispose();
    }
}
