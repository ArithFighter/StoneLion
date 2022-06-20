package com.arithfighter.not.entity.lion;

import com.arithfighter.not.card.CardAnimate;
import com.arithfighter.not.entity.player.Player;
import com.badlogic.gdx.graphics.Texture;

public class StoneLionEntity {
    private final StoneLion stoneLion;

    public StoneLionEntity(Texture texture, Player player, CardAnimate cardAnimate) {
        stoneLion = new StoneLion(texture) {
            @Override
            public void initCardPosition() {
                cardAnimate.getCardReset().setStart();
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                cardAnimate.getCardFadeOut().setStart();
                player.playCard();
            }
        };
        stoneLion.setPosition(900, 200);
    }

    public StoneLion getStoneLion() {
        return stoneLion;
    }
}
