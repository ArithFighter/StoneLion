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
    private final SceneControlButton returnButton;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private int remainingTokens;
    private ResultState state = ResultState.WIN;

    public ResultScreen(Texture[] textures){
        continueButton = new SceneControlButton(textures[6], 2);
        continueButton.getButton().setPosition(600,150);

        returnButton = new SceneControlButton(textures[6], 2);
        returnButton.getButton().setPosition(600,150);

        winOrLost = new Font(40);
        winOrLost.setColor(Color.WHITE);

        tokenMessage = new Font(32);
        tokenMessage.setColor(Color.WHITE);
    }

    public void setState(ResultState state){
        this.state = state;
    }

    public void setRemainingTokens(int tokens){
        remainingTokens = tokens;
    }

    @Override
    public void touchDown() {
        if (state == ResultState.WIN|| state == ResultState.LOOSE)
            continueButton.getButton().activate(cursorPos.getX(),cursorPos.getY());

        if (state == ResultState.OVER)
            returnButton.getButton().activate(cursorPos.getX(), cursorPos.getY());
    }

    @Override
    public void touchDragged() {
        if (state == ResultState.WIN|| state == ResultState.LOOSE)
            continueButton.getButton().deactivate();

        if (state == ResultState.OVER)
            returnButton.getButton().deactivate();
    }

    @Override
    public void touchUp() {
        if (state == ResultState.WIN|| state == ResultState.LOOSE)
            continueButton.getButton().deactivate();

        if (state == ResultState.OVER)
            returnButton.getButton().deactivate();
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
        returnButton.init();
    }

    @Override
    public void update() {
        continueButton.update();

        returnButton.update();
    }

    public boolean isContinue(){
        return continueButton.isStart();
    }

    public boolean isReturn(){
        return returnButton.isStart();
    }

    @Override
    public void draw() {
        String message = "";
        if (state == ResultState.WIN|| state == ResultState.LOOSE){
            if (state == ResultState.WIN)
                message = "You Win";
            if (state == ResultState.LOOSE)
                message = "You Loose";

            winOrLost.draw(batch, message, 600,500);

            tokenMessage.draw(batch, remainingTokens+" Tokens remain", 600,400);

            continueButton.getButton().draw(batch, "continue");
        }
        if (state == ResultState.OVER){
            message = "GAME OVER";
            winOrLost.draw(batch, message, 600,500);
            returnButton.getButton().draw(batch, "Return");
        }
    }

    @Override
    public void dispose() {
        winOrLost.dispose();
        tokenMessage.dispose();
        continueButton.dispose();
        returnButton.dispose();
    }
}
