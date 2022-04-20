package com.arithfighter.not.scene;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.pojo.ValueHolder;
import com.arithfighter.not.widget.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage extends SceneComponent implements SceneEvent, MouseEvent{
    private final PlayerCollection playerCollection;
    private final GamePlayComponent gamePlayComponent;
    private final SceneControlButton pauseButton;
    private final PauseMenu pauseMenu;
    private final GameDataDisplacer dataDisplacer;
    private final ValueHolder tokenHolder;
    private final Recorder playRecord = new Recorder();
    private int numberBoxQuantity;
    private final int initTokens = 10;
    private int cardLimit;

    public Stage(Texture[] textures, Texture[] cards, SoundManager soundManager) {
        dataDisplacer = new GameDataDisplacer();

        gamePlayComponent = new GamePlayComponent(textures, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        tokenHolder = new ValueHolder(initTokens);

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
        return tokenHolder.getValue() == 0;
    }

    public boolean isExceedCardLimit(){
        return playRecord.getRecord()>cardLimit;
    }

    public boolean isAllNumZero(){
        return gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
    }

    public ValueHolder getTokenHolder(){
        return tokenHolder;
    }

    public void setInitTokens(){
        tokenHolder.setInitValue(initTokens);
    }

    public boolean isOpenOption(){
        return pauseMenu.isOpenOption();
    }

    public boolean isQuit() {
        return pauseMenu.isReturnToMainMenu();
    }

    public void initPauseMenu(){
        pauseMenu.init();
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

            gamePlayComponent.update(getCursorPos().getX(), getCursorPos().getY());
        }
    }

    public void draw() {
        SpriteBatch batch = getBatch();

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
        dataDisplacer.setCursorPoint(getCursorPos().getX(), getCursorPos().getY());
        dataDisplacer.setToken(tokenHolder.getValue());
        dataDisplacer.setCardLimit(cardLimit);
        dataDisplacer.draw(getBatch());
    }

    public void setSelectedPlayerToGame(int i) {
        gamePlayComponent.setPlayer(playerCollection.getPlayers()[i]);
    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        if (pauseButton.isStart())
            pauseMenu.touchDown(x, y);
        else{
            pauseButton.getButton().activate(x, y);
            gamePlayComponent.touchDown(x, y);
        }
    }

    public void touchDragged() {
        if (pauseButton.isStart())
            pauseMenu.touchDragged();
        else{
            pauseButton.getButton().deactivate();
            gamePlayComponent.touchDragged(getCursorPos().getX(), getCursorPos().getY());
        }
    }

    public void touchUp() {
        if (pauseButton.isStart())
            pauseMenu.touchUp();
        else{
            pauseButton.getButton().deactivate();
            gamePlayComponent.touchUp(getCursorPos().getX(), getCursorPos().getY());
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