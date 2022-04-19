package com.arithfighter.not.scene;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.pojo.TokenHolder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage implements SceneEvent, MouseEvent{
    private final PlayerCollection playerCollection;
    private final GamePlayComponent gamePlayComponent;
    private final SceneControlButton pauseButton;
    private final PauseMenu pauseMenu;
    private final GameDataDisplacer dataDisplacer;
    private CursorPositionAccessor cursorPos;
    private final TokenHolder tokenHolder;
    private SpriteBatch batch;
    private final Recorder playRecord = new Recorder();
    private int numberBoxQuantity = 99;
    private final int initTokens = 10;
    private int cardLimit;

    public Stage(Texture[] textures, Texture[] cards, SoundManager soundManager) {
        dataDisplacer = new GameDataDisplacer();

        gamePlayComponent = new GamePlayComponent(textures, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        tokenHolder = new TokenHolder(initTokens);

        playerCollection = new PlayerCollection(textures, cards,
                CharacterList.values().length, gamePlayComponent.getNumberBoxDisplacer());
        playerCollection.setPlayRecord(playRecord);

        pauseButton = new SceneControlButton(textures[6], 1.8f);
        pauseButton.getButton().setPosition(1000,600);
    }

    public void setCardLimit(int limit){
        cardLimit = limit;
    }

    public boolean isNoTokens(){
        return tokenHolder.getToken() == 0;
    }

    public boolean isExceedCardLimit(){
        return playRecord.getRecord()>cardLimit;
    }

    public boolean isAllNumZero(){
        return gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
    }

    public TokenHolder getTokenHolder(){
        return tokenHolder;
    }

    public void setInitTokens(){
        tokenHolder.setInitToken(initTokens);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setCursorPos(CursorPositionAccessor cursorPos) {
        this.cursorPos = cursorPos;
    }

    public boolean isQuit() {
        return pauseMenu.isReturnToMainMenu();
    }

    public void init() {
        playRecord.reset();
        gamePlayComponent.init();
        pauseMenu.init();
        pauseButton.init();
    }

    public void setNumberBoxQuantity(int quantity){
        numberBoxQuantity = quantity;
    }

    public void update() {
        if (pauseButton.isStart()){
            pauseMenu.update();

            if (pauseMenu.isResume()){
                pauseButton.init();
                pauseMenu.init();
            }
        }else {
            pauseButton.update();

            gamePlayComponent.setNumberQuantity(numberBoxQuantity);

            gamePlayComponent.update(cursorPos.getX(), cursorPos.getY());
        }
    }

    public void draw() {
        gamePlayComponent.draw(batch);

        if (pauseButton.isStart())
            pauseMenu.draw(batch);
        else
            pauseButton.getButton().draw(batch, "Pause");
    }

    public void drawData(int index) {
        //for dev
        dataDisplacer.setCardPlayTimes(playRecord.getRecord());
        dataDisplacer.setScore(gamePlayComponent.getScore());
        dataDisplacer.setEnergy(playerCollection.getPlayers()[index].getEnergy());
        dataDisplacer.setCursorPoint(cursorPos.getX(), cursorPos.getY());
        dataDisplacer.setToken(tokenHolder.getToken());
        dataDisplacer.setCardLimit(cardLimit);
        dataDisplacer.draw(batch);
    }

    public void setSelectedPlayerToGame(int i) {
        gamePlayComponent.setPlayer(playerCollection.getPlayers()[i]);
    }

    public void touchDown() {
        if (pauseButton.isStart())
            pauseMenu.touchDown(cursorPos.getX(), cursorPos.getY());
        else{
            pauseButton.getButton().activate(cursorPos.getX(), cursorPos.getY());
            gamePlayComponent.touchDown(cursorPos.getX(), cursorPos.getY());
        }
    }

    public void touchDragged() {
        if (pauseButton.isStart())
            pauseMenu.touchDragged();
        else{
            pauseButton.getButton().deactivate();
            gamePlayComponent.touchDragged(cursorPos.getX(), cursorPos.getY());
        }
    }

    public void touchUp() {
        if (pauseButton.isStart())
            pauseMenu.touchUp();
        else{
            pauseButton.getButton().deactivate();
            gamePlayComponent.touchUp(cursorPos.getX(), cursorPos.getY());
        }
    }

    public void dispose() {
        dataDisplacer.dispose();
        gamePlayComponent.dispose();
        pauseMenu.dispose();
        playerCollection.dispose();
        pauseButton.dispose();
    }
}