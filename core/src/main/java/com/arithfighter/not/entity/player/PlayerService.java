package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.badlogic.gdx.graphics.Texture;

public class PlayerService {
    private final Player[] players;

    public PlayerService(Texture[] cards, SumBoxModel sumBoxModel) {
        players = new Player[CharacterList.values().length];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(cards, CharacterList.values()[i]) {
                @Override
                public void doWhenAnyCardPlayed() {
                    super.doWhenAnyCardPlayed();
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

    public Player[] getPlayers() {
        return players;
    }
}
