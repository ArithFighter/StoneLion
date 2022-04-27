package com.arithfighter.not.scene;

import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.GameRecorder;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.pojo.ValueHolder;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
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
    private final ConditionMessage conditionMessage;
    private int numberBoxQuantity;
    private int cardLimit;

    public Stage(TextureManager textureManager, SoundManager soundManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        Texture[] cards=textureManager.getTextures(textureManager.getKeys()[1]);
        Texture[] spriteSheet=textureManager.getTextures(textureManager.getKeys()[3]);

        dataDisplacer = new GameDataDisplacer();

        gamePlayComponent = new GamePlayComponent(textures, spriteSheet, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        tokenHolder = new ValueHolder(999);

        playerCollection = new PlayerCollection(textures, cards,
                CharacterList.values().length, gamePlayComponent.getNumberBoxDisplacer());
        playerCollection.setPlayRecord(playRecord);

        pauseButton = new SceneControlButton(textures[6], 1.8f);
        pauseButton.getButton().setPosition(1000,600);

        conditionMessage = new ConditionMessage(){
            @Override
            public boolean isExceedCardLimit() {
                return playRecord.getRecord()>=cardLimit&&!gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
            }

            @Override
            public boolean isAllNumZero() {
                return gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
            }
        };
    }

    public void setCardLimit(int limit){
        cardLimit = limit;
    }

    public void setGameRecorder(GameRecorder gameRecorder){
        pauseMenu.setGameRecorder(gameRecorder);
    }

    public boolean isWin(){
        return conditionMessage.isWin();
    }

    public boolean isLose(){
        return conditionMessage.isLose();
    }

    public ValueHolder getTokenHolder(){
        return tokenHolder;
    }

    public void setInitTokens(int initTokens){
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
        conditionMessage.init();
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

        conditionMessage.draw(batch);
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

        if (isStageNotComplete()) {
            if (pauseButton.isStart())
                pauseMenu.touchDown(x, y);
            else{
                pauseButton.getButton().activate(x, y);
                gamePlayComponent.touchDown(x, y);
            }
        }
    }

    public void touchDragged() {
        if (isStageNotComplete()){
            if (pauseButton.isStart())
                pauseMenu.touchDragged();
            else{
                pauseButton.getButton().deactivate();
                gamePlayComponent.touchDragged(getCursorPos().getX(), getCursorPos().getY());
            }
        }

    }

    public void touchUp() {
        if (isStageNotComplete()){
            if (pauseButton.isStart())
                pauseMenu.touchUp();
            else{
                pauseButton.getButton().deactivate();
                gamePlayComponent.touchUp(getCursorPos().getX(), getCursorPos().getY());
            }
        }
    }

    public boolean isStageNotComplete(){
        return !conditionMessage.isAllNumZero()&&!conditionMessage.isExceedCardLimit();
    }

    public void dispose() {
        dataDisplacer.dispose();
        gamePlayComponent.dispose();
        pauseMenu.dispose();
        playerCollection.dispose();
        pauseButton.dispose();
    }
}

class ConditionMessage{
    private final Font message;
    private boolean isWin = false;
    private boolean isLose = false;
    private final TimeHandler transitionHandler;

    public ConditionMessage(){
        message = new Font(45);
        message.setColor(Color.WHITE);

        transitionHandler = new TimeHandler();
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isLose() {
        return isLose;
    }

    public void init(){
        isWin = false;
        isLose = false;
        transitionHandler.resetPastedTime();
    }

    public void draw(SpriteBatch batch){
        String message = "";
        if (isAllNumZero()||isExceedCardLimit()){
            if (isAllNumZero())
                message = "Complete";
            if (isExceedCardLimit())
                message = "Exceed limit";

            transitionHandler.updatePastedTime();
            if (transitionHandler.getPastedTime()<2.5f)
                this.message.draw(batch, message, 500,400);
            else {
                if (isAllNumZero())
                    isWin = true;
                if (isExceedCardLimit())
                    isLose = true;
            }
        }
    }

    public boolean isExceedCardLimit() {
        return false;
    }

    public boolean isAllNumZero() {
        return false;
    }

    public void dispose(){
        message.dispose();
    }
}