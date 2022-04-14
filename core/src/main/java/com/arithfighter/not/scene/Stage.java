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
    private final PauseMenu pauseMenu;
    private final GameDataDisplacer dataDisplacer;
    private CursorPositionAccessor cursorPos;
    private final TokenHolder tokenHolder;
    private SpriteBatch batch;
    private final Recorder playRecord = new Recorder();
    private final int initTokens = 10;
    private int numberBoxQuantity = 99;

    public Stage(Texture[] textures, Texture[] cards, SoundManager soundManager) {
        dataDisplacer = new GameDataDisplacer();

        gamePlayComponent = new GamePlayComponent(textures, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        tokenHolder = new TokenHolder(initTokens);

        playerCollection = new PlayerCollection(textures, cards, CharacterList.values().length);
        playerCollection.setPlayRecord(playRecord);
        playerCollection.setNumberBoxDisplacer(gamePlayComponent.getNumberBoxDisplacer());
    }

    public boolean isExceedCardLimit(int limit){
        return playRecord.getRecord()>=limit;
    }

    public boolean isAllNumZero(){
        return gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
    }

    public TokenHolder getTokenHolder(){
        return tokenHolder;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setCursorPos(CursorPositionAccessor cursorPos) {
        this.cursorPos = cursorPos;
    }

    public boolean isReturnToMenu() {
        return pauseMenu.isReturnToMainMenu();
    }

    public void init() {
        tokenHolder.setInitToken(initTokens);
        playRecord.reset();
        gamePlayComponent.init();
        pauseMenu.init();
    }

    public void setNumberBoxQuantity(int quantity){
        numberBoxQuantity = quantity;
    }

    public void update() {
        pauseMenu.update();

        gamePlayComponent.setNumberQuantity(numberBoxQuantity);

        gamePlayComponent.update(cursorPos.getX(), cursorPos.getY());
    }

    public void draw() {
        pauseMenu.draw(batch);

        gamePlayComponent.draw(batch);
    }

    public void drawData(int index) {
        //for dev
        dataDisplacer.setCardPlayTimes(playRecord.getRecord());
        dataDisplacer.setScore(gamePlayComponent.getScore());
        dataDisplacer.setEnergy(playerCollection.getPlayers()[index].getEnergy());
        dataDisplacer.setCursorPoint(cursorPos.getX(), cursorPos.getY());
        dataDisplacer.setToken(tokenHolder.getToken());
        dataDisplacer.draw(batch);
    }

    public void setSelectedPlayerToGame(int i) {
        gamePlayComponent.setPlayer(playerCollection.getPlayers()[i]);
    }

    public void touchDown() {
        gamePlayComponent.touchDown(cursorPos.getX(), cursorPos.getY());

        pauseMenu.touchDown(cursorPos.getX(), cursorPos.getY());
    }

    public void touchDragged() {
        gamePlayComponent.touchDragged(cursorPos.getX(), cursorPos.getY());

        pauseMenu.touchDragged();
    }

    public void touchUp() {
        gamePlayComponent.touchUp(cursorPos.getX(), cursorPos.getY());

        pauseMenu.touchUp();
    }

    public void dispose() {
        dataDisplacer.dispose();
        gamePlayComponent.dispose();
        pauseMenu.dispose();
        playerCollection.dispose();
    }
}