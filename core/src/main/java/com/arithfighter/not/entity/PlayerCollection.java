package com.arithfighter.not.entity;

import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.system.GameNumProducer;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class PlayerCollection {
    private final Player[] players;
    private Recorder playRecord;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private final int characterQuantity;

    public PlayerCollection(Texture[] textures, Texture[] cards,
                            int characterQuantity, NumberBoxDisplacer numberBoxDisplacer) {
        this.characterQuantity = characterQuantity;
        players = new Player[characterQuantity];
        this.numberBoxDisplacer = numberBoxDisplacer;
        createPlayers(textures, cards);
    }

    public void setPlayRecord(Recorder playRecord) {
        this.playRecord = playRecord;
    }

    private void createPlayers(Texture[] textures, Texture[] cards) {
        SkillHandler skillHandler = new SkillHandler(numberBoxDisplacer);

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

    public Player[] getPlayers() {
        return players;
    }

    public void dispose() {
        for (Player player : players) player.dispose();
    }
}

class SkillHandler {
    private final NumberBoxDisplacer numberBoxDisplacer;
    private final NumberBoxPicker numberBoxPicker;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer) {
        this.numberBoxDisplacer = numberBoxDisplacer;
        numberBoxPicker = new NumberBoxPicker(numberBoxDisplacer);
    }

    public void cast(CharacterList character) {
        switch (character) {
            case KNIGHT:
                changeANumberBoxValue(9);
                break;
            case ROGUE:
                changeANumberBoxValue(8);
                break;
            case HUNTER:
                changeANumberBoxValue(14);
                break;
            case PALADIN:
                changeANumberBoxValue(22);
                break;
            case WARRIOR:
                increaseAllValueBy11();
                break;
        }
    }

    private void increaseAllValueBy11() {
        int gain = 11;

        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++) {
            if (getNumberBoxValue(i) > 0 &&
                    getNumberBoxValue(i) < new GameNumProducer().getMaxNumber() - gain)
                numberBoxDisplacer.set(i, getNumberBoxValue(i) + gain);
        }
    }

    private void changeANumberBoxValue(int value){
        numberBoxDisplacer.set(numberBoxPicker.getRandomNonZeroValueIndex(), value);
    }

    private int getNumberBoxValue(int i) {
        return numberBoxDisplacer.getNumberBoxValue(i);
    }
}

class NumberBoxPicker {
    private final NumberBoxDisplacer numberBoxDisplacer;

    public NumberBoxPicker(NumberBoxDisplacer numberBoxDisplacer) {
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public int getRandomNonZeroValueIndex() {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++) {
            if (numberBoxDisplacer.getNumberBoxValue(i) > 0)
                indexList.add(i);
        }
        RandomNumProducer rnp = new RandomNumProducer(indexList.size() - 1,0);
        int indexPick = rnp.getRandomNum();

        return indexList.get(indexPick);
    }
}
