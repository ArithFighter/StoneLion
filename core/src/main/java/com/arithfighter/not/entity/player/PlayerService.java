package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.numberbox.NumberBoxEntity;
import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.arithfighter.not.system.GameNumProducer;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class PlayerService {
    private Player[] players;
    private Recorder playRecord;
    private SumBoxModel sumBoxModel;
    private int characterQuantity;

    public PlayerService() {
    }

    public void setSumBoxModel(SumBoxModel sumBoxModel) {
        this.sumBoxModel = sumBoxModel;
    }

    public void setCharacterQuantity(int characterQuantity) {
        this.characterQuantity = characterQuantity;
    }

    public void setPlayRecord(Recorder playRecord) {
        this.playRecord = playRecord;
    }

    public void createPlayers(Texture[] textures, Texture[] cards) {
        players = new Player[characterQuantity];

        for (int i = 0; i < characterQuantity; i++)
            players[i] = new Player(cards, CharacterList.values()[i]) {
                @Override
                public void checkNumberCardPlayed() {
                    sumBoxModel.update(getHand().getCardNumber());
                }

                @Override
                public void doWhenResettingCardPlay() {
                    sumBoxModel.init();
                    sumBoxModel.update(getHand().getCardNumber());
                }

                @Override
                public void doWhenAnyCardPlayed() {
                    playRecord.update(1);
                }
            };
    }

    public Player[] getPlayers() {
        return players;
    }
}
