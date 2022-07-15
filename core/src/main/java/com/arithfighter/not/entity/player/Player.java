package com.arithfighter.not.entity.player;

import com.arithfighter.not.card.NumberCard;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final NumberCardService numberCardService;

    public Player(Texture[] cards, CharacterList character) {
        numberCardService = new NumberCardService(cards, character);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(9,2);

        numberCardService.setInitPoint(new Point(
                layoutSetter.getGrid().getWidth()*5,
                numberCardService.getCards()[0].getRectangle().getHeight()*-1/3
        ));
    }

    public NumberCardService getHand() {
        return numberCardService;
    }

    public boolean isCardActive(){
        return numberCardService.isCardActive();
    }

    public NumberCard getActiveCard() {
        return numberCardService.getActiveCard();
    }

    public final void activateCard(int mouseX, int mouseY) {
        for (NumberCard card : numberCardService.getCards())
            card.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY) {
        for (NumberCard card : numberCardService.getCards()){
            if (card.isActive())
                card.getPoint().set(
                        mouseX - card.getRectangle().getWidth() / 2,
                            mouseY - card.getRectangle().getHeight() / 2
                );
        }
    }

    public final void draw(SpriteBatch batch) {
        numberCardService.draw(batch);
    }

    public void playOnCardAnimation(int mouseX, int mouseY) {
        for (NumberCard card : numberCardService.getCards()) {
            if (card.isOnCard(mouseX, mouseY)) {
                moveCardUpward(card);
            } else
                card.initCard();
        }
    }

    private void moveCardUpward(NumberCard card){
        float movingDistance = card.getRectangle().getHeight()/3;
        float speed = 3;

        if (card.getPoint().getY() < card.getInitPoint().getY() + movingDistance)
            card.getPoint().set(card.getPoint().getX(), card.getPoint().getY()+speed);
    }

    public final void initHand() {
        for (NumberCard card : numberCardService.getCards())
            card.initCard();
    }

    public final void playCard() {
        if (numberCardService.isCardActive()) {
            doWhenAnyCardPlayed();

            if (numberCardService.isResettingCard())
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

