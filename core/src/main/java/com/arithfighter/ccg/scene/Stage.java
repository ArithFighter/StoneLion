package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.audio.SoundManager;
import com.arithfighter.ccg.entity.*;
import com.arithfighter.ccg.entity.player.CharacterList;
import com.arithfighter.ccg.entity.player.Player;
import com.arithfighter.ccg.CursorPositionAccessor;
import com.arithfighter.ccg.pojo.GameNumProducer;
import com.arithfighter.ccg.pojo.Recorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Stage implements SceneEvent, MouseEvent{
    private final Player[] players;
    private final StageComponent stageComponent;
    private final PauseMenu pauseMenu;
    private final GameDataDisplacer dataAccessor;
    private CursorPositionAccessor cursorPos;
    private final int characterQuantity = CharacterList.values().length;
    private SpriteBatch batch;
    private final Recorder playRecord = new Recorder();

    public Stage(Texture[] textures, Texture[] cards, SoundManager soundManager) {
        dataAccessor = new GameDataDisplacer();

        players = new Player[characterQuantity];

        stageComponent = new StageComponent(textures, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        createPlayers(textures, cards);
    }

    private void createPlayers(Texture[] textures, Texture[] cards) {
        SkillHandler skillHandler = new SkillHandler(stageComponent.getNumberBoxDisplacer());

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
        playRecord.reset();
        stageComponent.init();
        pauseMenu.init();
    }

    public void update() {
        pauseMenu.update();

        stageComponent.update(cursorPos.getX(), cursorPos.getY());

        //This is for developer, will remove in open version
        manualReset();
    }

    private void manualReset() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            playRecord.reset();
            stageComponent.init();
        }
    }

    public void draw() {
        pauseMenu.draw(batch);

        stageComponent.draw(batch);
    }

    public void drawData(int index) {
        dataAccessor.setCardPlayTimes(playRecord.getRecord());
        dataAccessor.setScore(stageComponent.getScore());
        dataAccessor.draw(cursorPos.getX(), cursorPos.getY(), players[index].getEnergy(), batch);//for dev
    }

    public void setSelectedPlayerToGame(int i) {
        stageComponent.setPlayer(players[i]);
    }

    public void touchDown() {
        stageComponent.touchDown(cursorPos.getX(), cursorPos.getY());

        pauseMenu.touchDown(cursorPos.getX(), cursorPos.getY());
    }

    public void touchDragged() {
        stageComponent.touchDragged(cursorPos.getX(), cursorPos.getY());

        pauseMenu.touchDragged();
    }

    public void touchUp() {
        stageComponent.touchUp(cursorPos.getX(), cursorPos.getY());

        pauseMenu.touchUp();
    }

    public void dispose() {
        dataAccessor.dispose();
        stageComponent.dispose();
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
                replaceOneNonZeroValue(16);
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

        int indexPick = getRandomNum(indexList.size());

        return indexList.get(indexPick);
    }


    private int getRandomNum(int range) {
        return (int) (Math.random() * (range + 1) - 1);
    }
}
