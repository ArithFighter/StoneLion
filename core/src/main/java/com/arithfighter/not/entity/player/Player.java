package com.arithfighter.not.entity.player;

import com.arithfighter.not.card.NumberCard;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Hand hand;

    public Player(Texture[] cards, CharacterList character) {
        hand = new Hand(cards, character);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(9,2);

        hand.setInitPoint(new Point(layoutSetter.getGrid().getWidth()*5,hand.getCards()[0].getShape().getHeight()*-1/3));
    }

    public Hand getHand() {
        return hand;
    }

    public boolean isCardActive(){
        return hand.isCardActive();
    }

    public NumberCard getActiveCard() {
        return hand.getActiveCard();
    }

    public final void activateCard(int mouseX, int mouseY) {
        for (NumberCard card : hand.getCards())
            card.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY) {
        for (NumberCard card : hand.getCards()){
            if (card.isActive())
                card.getPoint().set(
                        mouseX - card.getShape().getWidth() / 2,
                            mouseY - card.getShape().getHeight() / 2
                );
        }
    }

    public final void draw(SpriteBatch batch) {
        hand.draw(batch);
    }

    public void playOnCardAnimation(int mouseX, int mouseY) {
        for (NumberCard card : hand.getCards()) {
            if (card.isOnCard(mouseX, mouseY)) {
                moveCardUpward(card);
            } else
                card.initCard();
        }
    }

    private void moveCardUpward(NumberCard card){
        float movingDistance = card.getShape().getHeight()/3;
        float speed = 3;

        if (card.getPoint().getY() < card.getInitPoint().getY() + movingDistance)
            card.getPoint().set(card.getPoint().getX(), card.getPoint().getY()+speed);
    }

    public final void initHand() {
        for (NumberCard card : hand.getCards())
            card.initCard();
    }

    public final void playCard() {
        if (hand.isCardActive()) {
            doWhenAnyCardPlayed();

            if (hand.isResettingCard())
                doWhenResettingCardPlay();
            else
                checkNumberCardPlayed();
        }
    }

    public void doWhenAnyCardPlayed() {
    }

    public void checkNumberCardPlayed() {
    }

    public void doWhenResettingCardPlay() {
    }
}

