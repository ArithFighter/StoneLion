package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Texture;

public class PlayerService {
    private final Player[] players;
    private SumBoxModel sumBoxModel;
    private Recorder remainCardRecorder;

    public PlayerService(Texture[] cards) {
        players = new Player[CharacterList.values().length];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(cards, CharacterList.values()[i]) {
                @Override
                public void doWhenAnyCardPlayed() {
                    if (remainCardRecorder.getRecord()>=0)
                        remainCardRecorder.update(-1);
                }

                @Override
                public void checkNumberCardPlayed() {
                    sumBoxModel.update(getActiveCardNumber());
                }

                @Override
                public void doWhenResettingCardPlay() {
                    sumBoxModel.init();
                    sumBoxModel.update(getActiveCardNumber());
                }
            };
        }
    }

    public void setRemainCardRecorder(Recorder remainCardRecorder) {
        this.remainCardRecorder = remainCardRecorder;
    }

    public void setSumBoxModel(SumBoxModel sumBoxModel) {
        this.sumBoxModel = sumBoxModel;
    }

    public Player[] getPlayers() {
        return players;
    }
}
