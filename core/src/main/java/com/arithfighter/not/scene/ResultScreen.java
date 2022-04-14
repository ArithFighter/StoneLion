package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ResultScreen implements SceneEvent, MouseEvent{
    private final Font winOrLost;
    private final Font tokenMessage;
    private final SceneControlButton continueButton;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private int remainingTokens;
    private boolean isWin = false;

    public ResultScreen(Texture[] textures){
        continueButton = new SceneControlButton(textures[6], 2);
        continueButton.getButton().setPosition(600,150);

        winOrLost = new Font(40);
        winOrLost.setColor(Color.WHITE);

        tokenMessage = new Font(32);
        tokenMessage.setColor(Color.WHITE);
    }

    public void setWin(boolean isWin){
        this.isWin = isWin;
    }

    public void setRemainingTokens(int tokens){
        remainingTokens = tokens;
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
        String message = "";
        if (isWin)
            message = "You Win";
        else
            message = "You Loose";

        winOrLost.draw(batch, message, 600,500);

        tokenMessage.draw(batch, remainingTokens+" Tokens remain", 600,400);

        continueButton.getButton().draw(batch, "continue");
    }

    @Override
    public void dispose() {
        winOrLost.dispose();
        tokenMessage.dispose();
        continueButton.dispose();
    }
}