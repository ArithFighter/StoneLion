package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.badlogic.gdx.graphics.Texture;

public class PlayerService {
    private final Player[] players;
    private SumBoxModel sumBoxModel;
    private int remainCards;

    public PlayerService(Texture[] cards) {
        players = new Player[CharacterList.values().length];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(cards, CharacterList.values()[i]) {
                @Override
                public void doWhenAnyCardPlayed() {
                    remainCards--;
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

    public int getRemainCards() {
        return remainCards;
    }

    public void setRemainCards(int remainCards) {
        this.remainCards = remainCards;
    }

    public void setSumBoxModel(SumBoxModel sumBoxModel) {
        this.sumBoxModel = sumBoxModel;
    }

    public Player[] getPlayers() {
        return players;
    }
}
