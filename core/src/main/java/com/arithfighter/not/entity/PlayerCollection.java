package com.arithfighter.not.entity;

import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.pojo.GameNumProducer;
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
    private final NumberBoxIndexPicker numberBoxIndexPicker;

    public SkillHandler(NumberBoxDisplacer numberBoxDisplacer) {
        this.numberBoxDisplacer = numberBoxDisplacer;
        numberBoxIndexPicker = new NumberBoxIndexPicker(numberBoxDisplacer);
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
                    getNumberBoxValue(i) < new GameNumProducer().getMax() - gain)
                numberBoxDisplacer.set(i, getNumberBoxValue(i) + gain);
        }
    }

    private void replaceOneNonZeroValue(int value) {
        int index = numberBoxIndexPicker.getRandomNonZeroValueIndex();

        numberBoxDisplacer.set(index, value);
    }

    private int getNumberBoxValue(int i) {
        return numberBoxDisplacer.getNumberBoxValue(i);
    }
}

class NumberBoxIndexPicker {
    private final NumberBoxDisplacer numberBoxDisplacer;

    public NumberBoxIndexPicker(NumberBoxDisplacer numberBoxDisplacer) {
        this.numberBoxDisplacer = numberBoxDisplacer;
    }

    public int getRandomNonZeroValueIndex() {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxDisplacer.getMaxQuantity(); i++) {
            if (numberBoxDisplacer.getNumberBoxValue(i) > 0)
                indexList.add(i);
        }

        int indexPick = getRandomNum(indexList.size() - 1);

        return indexList.get(indexPick);
    }


    private int getRandomNum(int range) {
        return (int) (Math.random() * (range + 1));
    }
}
