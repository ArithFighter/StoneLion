package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.sum.SumDisplacerModel;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Texture;

public class PlayerService {
    private final Player[] players;
    private SumDisplacerModel sumDisplacerModel;
    private Recorder remainPlayTimeRecorder;

    public PlayerService(Texture[] cards) {
        players = new Player[CharacterList.values().length];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(cards, CharacterList.values()[i]) {
                @Override
                public void doWhenAnyCardPlayed() {
                    if (remainPlayTimeRecorder.getRecord()>=0)
                        remainPlayTimeRecorder.update(-1);
                }

                @Override
                public void checkNumberCardPlayed() {
                    sumDisplacerModel.update(getActiveCardNumber());
                }

                @Override
                public void doWhenResettingCardPlay() {
                    sumDisplacerModel.init();
                    sumDisplacerModel.update(getActiveCardNumber());
                }
            };
        }
    }

    public void setRemainPlayTimeRecorder(Recorder remainPlayTimeRecorder) {
        this.remainPlayTimeRecorder = remainPlayTimeRecorder;
    }

    public void setSumBoxModel(SumDisplacerModel sumDisplacerModel) {
        this.sumDisplacerModel = sumDisplacerModel;
    }

    public Player[] getPlayers() {
        return players;
    }
}
