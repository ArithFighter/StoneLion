package com.arithfighter.not.scene;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.pojo.GameNumProducer;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.pojo.TokenHolder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Stage implements SceneEvent, MouseEvent{
    private final Player[] players;
    private final GamePlayComponent gamePlayComponent;
    private final PauseMenu pauseMenu;
    private final GameDataDisplacer dataDisplacer;
    private CursorPositionAccessor cursorPos;
    private final TokenHolder tokenHolder;
    private final int characterQuantity = CharacterList.values().length;
    private SpriteBatch batch;
    private final Recorder playRecord = new Recorder();
    private final int initTokens = 10;
    private int numberBoxQuantity = 99;

    public Stage(Texture[] textures, Texture[] cards, SoundManager soundManager) {
        dataDisplacer = new GameDataDisplacer();

        players = new Player[characterQuantity];

        gamePlayComponent = new GamePlayComponent(textures, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        tokenHolder = new TokenHolder(initTokens);

        createPlayers(textures, cards);
    }

    private void createPlayers(Texture[] textures, Texture[] cards) {
        SkillHandler skillHandler = new SkillHandler(gamePlayComponent.getNumberBoxDisplacer());

        for (int i = 0; i < characterQuantity; i++)
            players[i] = new Player(
                    textures,
                    cards,
                    CharacterList.values()[i]) {
                @Override
                public void doWhenCardPlayed() {
                    playRecord.update(1);
                }

                @Override
                public void castSkill(CharacterList character) {
                    skillHandler.cast(character);
                }
            };
    }

    public boolean isAllNumZero(){
        return gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
    }

    public int getTokens(){
        return tokenHolder.getToken();
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
        dataDisplacer.setEnergy(players[index].getEnergy());
        dataDisplacer.setCursorPoint(cursorPos.getX(), cursorPos.getY());
        dataDisplacer.setToken(tokenHolder.getToken());
        dataDisplacer.draw(batch);
    }

    public void setSelectedPlayerToGame(int i) {
        gamePlayComponent.setPlayer(players[i]);
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
        for (Player player : players) player.dispose();
    }
}

class SkillHandler {
    private final NumberBoxDisplacer numberBoxDisplacer;
    private final IndexPicker indexPicker;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer) {
        this.numberBoxDisplacer = numberBoxDisplacer;
        indexPicker = new IndexPicker(numberBoxDisplacer);
    }

    public void cast(CharacterList character) {
        switch (character) {
            case KNIGHT:
                replaceOneNonZeroValue(9);
                break;
            case ROGUE:
                replaceOneNonZeroValue(8);
                break;
            case HUNTER:
                replaceOneNonZeroValue(14);
                break;
            case PALADIN:
                replaceOneNonZeroValue(22);
                break;
            case WARRIOR:
                increaseAllValueByFour();
                break;
        }
    }

    private void increaseAllValueByFour() {
        int gain = 4;

        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++) {
            if (getNumberBoxValue(i) > 0 &&
                    getNumberBoxValue(i) < new GameNumProducer().getMax()-gain)
                numberBoxDisplacer.set(i, getNumberBoxValue(i) + gain);
        }
    }

    private void replaceOneNonZeroValue(int value) {
        int index = indexPicker.getRandomNonZeroValueIndex();

        numberBoxDisplacer.set(index, value);
    }

    private int getNumberBoxValue(int i) {
        return numberBoxDisplacer.getNumberBoxValue(i);
    }
}

class IndexPicker {
    private final NumberBoxDisplacer numberBoxDisplacer;

    public IndexPicker(NumberBoxDisplacer numberBoxDisplacer) {
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public int getRandomNonZeroValueIndex() {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++) {
            if (numberBoxDisplacer.getNumberBoxValue(i) > 0)
                indexList.add(i);
        }

        int indexPick = getRandomNum(indexList.size()-1);

        return indexList.get(indexPick);
    }


    private int getRandomNum(int range) {
        return (int) (Math.random() * (range + 1));
    }
}
