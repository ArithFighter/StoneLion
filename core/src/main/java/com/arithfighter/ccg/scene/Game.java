package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.SoundManager;
import com.arithfighter.ccg.entity.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Game {
    private final Player[] players;
    private final GameComponent gameComponent;
    private final GameDataAccessor dataAccessor;
    private final int characterQuantity = CharacterList.values().length;
    private SpriteBatch batch;

    public Game(Texture[] textures, Texture[] cards, SoundManager soundManager){
        dataAccessor = new GameDataAccessor();

        players = new Player[characterQuantity];

        gameComponent = new GameComponent(textures, dataAccessor, soundManager);

        addPlayers(textures, cards);
    }

    private void addPlayers(Texture[] textures, Texture[] cards) {
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

    public boolean isReturnToMenu(){
        return gameComponent.isReturnToMenu();
    }

    public void init(){
        gameComponent.init();
        dataAccessor.resetRecorder();
    }

    public void update(int mouseX, int mouseY){
        gameComponent.update(mouseX, mouseY);
    }

    public void draw(){
        gameComponent.draw(batch);
    }

    public void drawData(int mouseX, int mouseY, int index){
        dataAccessor.draw(mouseX, mouseY, players[index].getEnergy(), batch);//for dev
    }

    public void setCurrentPlayerToGame(int i){
        gameComponent.setPlayer(players[i]);
    }

    public void touchDown(int mouseX, int mouseY){
        gameComponent.getPlayer().activateCard(mouseX, mouseY);

        gameComponent.getReturnButton().activate(mouseX, mouseY);
    }

    public void touchDragged(int mouseX, int mouseY){
        gameComponent.getPlayer().updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY){
        gameComponent.getBoardArea().playCardOnBoard(mouseX, mouseY);

        gameComponent.getReturnButton().deactivate();
    }

    public void dispose(){
        dataAccessor.dispose();
        gameComponent.dispose();
        for (Player player : players) player.dispose();
    }
}

class SkillHandler {
    NumberBoxDisplacer numberBoxDisplacer;
    NumBoxOperator operator;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;

        operator = new NumBoxOperator(this.numberBoxDisplacer);
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
                replaceOneNonZeroValue(31);
                break;
            case WARRIOR:
                increaseAllNonZeroValueByOne();
                break;
        }
    }

    private void increaseAllNonZeroValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (operator.isValueBiggerThanZero(i))
                numberBoxDisplacer.set(i, operator.getNumberBoxValue(i) + 1);
        }
    }

    private void reduceAllNonZeroValueByOne(){
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (operator.isValueBiggerThanZero(i))
                numberBoxDisplacer.set(i, operator.getNumberBoxValue(i) - 1);
        }
    }

    private void increaseOneNonZeroValueBySix(){
        int index = operator.getRandomNonZeroValueIndex();
        numberBoxDisplacer.set(index, operator.getNumberBoxValue(index)+6);
    }

    private void replaceOneNonZeroValue(int value){
        int index = operator.getRandomNonZeroValueIndex();
        numberBoxDisplacer.set(index, value);
    }
}

class NumBoxOperator{
    NumberBoxDisplacer numberBoxDisplacer;

    public NumBoxOperator(NumberBoxDisplacer numberBoxDisplacer){
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public int getRandomNonZeroValueIndex(){
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getNumberBoxQuantity(); i++){
            if (isValueBiggerThanZero(i))
                indexList.add(i);
        }

        int indexPick = getRandomNum(indexList.size());

        return indexList.get(indexPick);
    }

    public boolean isValueBiggerThanZero(int i){
        return getNumberBoxValue(i) > 0;
    }

    public int getNumberBoxValue(int i){
        return numberBoxDisplacer.getNumberList().get(i);
    }

    private int getRandomNum(int range){
        return (int)(Math.random() * (range + 1) - 1);
    }
}
