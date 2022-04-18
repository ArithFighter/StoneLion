package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.ControlNumber;
import com.arithfighter.not.entity.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetScreen implements SceneEvent, MouseEvent{
    private final Font cardLimitMessage;
    private final Font betMessage;
    private final ControlNumber tokenBet;
    private final SceneControlButton startButton;
    private SpriteBatch batch;
    private CursorPositionAccessor cursorPos;
    private final SoundManager soundManager;
    private final int numberBoxQuantity = 6;
    private int cardLimit;
    private int initToken;

    public BetScreen(Texture[] textures, SoundManager soundManager){
        this.soundManager = soundManager;

        tokenBet = new ControlNumber(textures);
        tokenBet.setPosition(500,300);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setPosition(1000,150);

        cardLimitMessage = new Font(40);
        cardLimitMessage.setColor(Color.WHITE);

        betMessage = new Font(30);
        betMessage.setColor(Color.WHITE);
    }

    public int getCardLimit(){
        return cardLimit;
    }

    public int getNumberBoxQuantity(){
        return numberBoxQuantity;
    }

    public void setToken(int value){
        initToken = value;
        tokenBet.setInitValue(initToken);
    }

    public int getBet(){
        return tokenBet.getValue();
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
        if (tokenBet.isButtonActive())
            soundManager.playTouchedSound();

        if (startButton.getButton().isActive())
            soundManager.playAcceptSound();

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
        startButton.init();
    }

    @Override
    public void update() {
        startButton.update();
        tokenBet.update();

        int valueChange = initToken/5;
        tokenBet.setValueChange(valueChange);

        float betTokensProportion = (initToken-tokenBet.getValue())/(float)initToken;

        cardLimit = (int) (betTokensProportion*numberBoxQuantity+1.6f*numberBoxQuantity);
    }

    @Override
    public void draw() {
        cardLimitMessage.draw(batch, "card limit: "+ cardLimit, 400,600);

        betMessage.draw(batch, "please bet", 400,400);

        tokenBet.draw(batch);

        startButton.getButton().draw(batch, "Start");
    }

    @Override
    public void dispose() {
        betMessage.dispose();
        cardLimitMessage.dispose();
        tokenBet.dispose();
        startButton.dispose();
    }
}
