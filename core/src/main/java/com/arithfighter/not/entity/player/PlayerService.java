package com.arithfighter.not.entity.player;

import com.arithfighter.not.entity.sumbox.SumBoxModel;
import com.badlogic.gdx.graphics.Texture;

public class PlayerService {
    private final Player player;

    public PlayerService(Texture[] cards, SumBoxModel sumBoxModel, CharacterList character) {
        player = new Player(cards, character) {
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

    public Player getPlayer() {
        return player;
    }
}
