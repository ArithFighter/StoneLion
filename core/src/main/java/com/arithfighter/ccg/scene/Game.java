package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.SoundManager;
import com.arithfighter.ccg.entity.*;
import com.arithfighter.ccg.entity.player.CharacterList;
import com.arithfighter.ccg.entity.player.Player;
import com.arithfighter.ccg.system.CursorPositionAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Game {
    private final Player[] players;
    private final GameComponent gameComponent;
    private final PauseMenu pauseMenu;
    private final GameDataAccessor dataAccessor;
    private CursorPositionAccessor cursorPos;
    private final int characterQuantity = CharacterList.values().length;
    private SpriteBatch batch;

    public Game(Texture[] textures, Texture[] cards, SoundManager soundManager){
        cursorPos = new CursorPositionAccessor();

        dataAccessor = new GameDataAccessor();

        players = new Player[characterQuantity];

        gameComponent = new GameComponent(textures, dataAccessor, soundManager);

        pauseMenu = new PauseMenu(textures);

        createPlayers(textures, cards);
    }

    private void createPlayers(Texture[] textures, Texture[] cards) {
        SkillHandler skillHandler = new SkillHandler(gameComponent.getNumberBoxDisplacer());

        for (int i = 0; i < characterQuantity; i++)
            players[i] = new Player(
                    textures,
                    cards,
                    CharacterList.values()[i]) {
                @Override
                public void doWhenCardPlayed() {
                    dataAccessor.updatePlayTimes();
                }

                @Override
                public void castSkill(CharacterList character) {
                    skillHandler.cast(character);
                }
            };
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }

    public void setCursorPos(CursorPositionAccessor cursorPos){
        this.cursorPos = cursorPos;
    }

    public boolean isReturnToMenu(){
        return pauseMenu.isReturnToMainMenu();
    }

    public void init(){
        gameComponent.init();
        pauseMenu.init();
        dataAccessor.resetRecorder();
    }

    public void update(){
        pauseMenu.update();

        gameComponent.update(cursorPos.getX(), cursorPos.getY());

        //This is for developer, will remove in open version
        manualReset();
    }

    private void manualReset(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameComponent.init();
            dataAccessor.resetRecorder();
        }
    }

    public void draw(){
        pauseMenu.draw(batch);

        gameComponent.draw(batch);
    }

    public void drawData(int index){
        dataAccessor.draw(cursorPos.getX(), cursorPos.getY(), players[index].getEnergy(), batch);//for dev
    }

    public void setSelectedPlayerToGame(int i){
        gameComponent.setPlayer(players[i]);
    }

    public void touchDown(int mouseX, int mouseY){
        gameComponent.touchDown(mouseX, mouseY);

        pauseMenu.touchDown(mouseX,mouseY);
    }

    public void touchDragged(int mouseX, int mouseY){
        gameComponent.touchDragged(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY){
        gameComponent.touchUp(mouseX, mouseY);

        pauseMenu.touchUp();
    }

    public void dispose(){
        dataAccessor.dispose();
        gameComponent.dispose();
        pauseMenu.dispose();
        for (Player player : players) player.dispose();
    }
}

class SkillHandler {
    private final NumberBoxDisplacer numberBoxDisplacer;
    private final IndexPicker indexPicker;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;
        indexPicker = new IndexPicker(numberBoxDisplacer);
    }

    public void cast(CharacterList character){
        switch (character) {
            case KNIGHT:
                increaseOneNonZeroValueBySix();
                break;
            case ROGUE:
                replaceOneNonZeroValue(16);
                break;
            case HUNTER:
                reduceAllNonZeroValueByOne();
                break;
            case PALADIN:
                replaceOneNonZeroValue(30);
                break;
            case WARRIOR:
                increaseAllNonZeroValueByOne();
                break;
        }
    }

    private void increaseAllNonZeroValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++){
            if (getNumberBoxValue(i)>0)
                numberBoxDisplacer.set(i, getNumberBoxValue(i) + 1);
        }
    }

    private void reduceAllNonZeroValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++){
            if (getNumberBoxValue(i)>0)
                numberBoxDisplacer.set(i, getNumberBoxValue(i) - 1);
        }
    }

    private void increaseOneNonZeroValueBySix(){
        int index = indexPicker.getRandomNonZeroValueIndex();
        numberBoxDisplacer.set(index, getNumberBoxValue(index)+6);
    }

    private void replaceOneNonZeroValue(int value){
        int index = indexPicker.getRandomNonZeroValueIndex();
        numberBoxDisplacer.set(index, value);
    }

    public int getNumberBoxValue(int i){
        return numberBoxDisplacer.getNumberBoxValue(i);
    }
}

class IndexPicker {
    private final NumberBoxDisplacer numberBoxDisplacer;

    public IndexPicker(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public int getRandomNonZeroValueIndex(){
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++){
            if (numberBoxDisplacer.getNumberBoxValue(i)>0)
                indexList.add(i);
        }

        int indexPick = getRandomNum(indexList.size());

        return indexList.get(indexPick);
    }


    private int getRandomNum(int range){
        return (int)(Math.random() * (range + 1)-1);
    }
}
