package com.arithfighter.not.entity.lion;

import com.arithfighter.not.card.CardAnimationEntity;
import com.arithfighter.not.entity.player.Player;
import com.badlogic.gdx.graphics.Texture;

public class StoneLionEntity {
    private final StoneLion stoneLion;
    private CardAnimationEntity cardAnimate;
    private Player player;

    public StoneLionEntity(Texture texture) {
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

    public void setCardAnimate(CardAnimationEntity cardAnimate) {
        this.cardAnimate = cardAnimate;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public StoneLion getStoneLion() {
        return stoneLion;
    }
}
