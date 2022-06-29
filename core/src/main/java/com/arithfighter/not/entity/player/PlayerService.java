package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Texture;

public class PlayerService {
    private final Player[] players;
    private SumBoxModel sumBoxModel;
    private Recorder remainCards;

    public PlayerService(Texture[] cards) {
        players = new Player[CharacterList.values().length];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(cards, CharacterList.values()[i]) {
                @Override
                public void doWhenAnyCardPlayed() {
                    if (remainCards.getRecord()>=0)
                        remainCards.update(-1);
                }

                @Override
                public void checkNumberCardPlayed() {
                    sumBoxModel.update(getHand().getCardNumber());
                }

                @Override
                public void doWhenResettingCardPlay() {
                    sumBoxModel.init();
                    sumBoxModel.update(getHand().getCardNumber());
                }
            };
        }
    }

    public Recorder getRemainCards() {
        return remainCards;
    }

    public void setRemainCards(Recorder remainCards) {
        this.remainCards = remainCards;
    }

    public void setSumBoxModel(SumBoxModel sumBoxModel) {
        this.sumBoxModel = sumBoxModel;
    }

    public Player[] getPlayers() {
        return players;
    }
}
