package com.arithfighter.not.entity.player;

import com.arithfighter.not.card.NumberCard;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Hand hand;
    private final CapacityManager capacityManager;
    private final Recorder sumAccessor;
    private final CharacterList character;
    private final PlayerEnergyBar energyBar;

    private enum SkillState {NEUTRAL, READY}

    private SkillState skillState = SkillState.NEUTRAL;

    public Player(Texture[] textures, Texture[] cards, CharacterList character) {
        energyBar = new PlayerEnergyBar(textures, character);

        hand = new Hand(cards, character);

        capacityManager = new CapacityManager();

        sumAccessor = new Recorder();

        this.character = character;
    }

    public void init() {
        sumAccessor.reset();
        capacityManager.initialize();
        energyBar.reset();
    }

    public final void activateCard(int mouseX, int mouseY) {
        for (NumberCard card : hand.getCards())
            card.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY) {
        for (NumberCard card : hand.getCards()){
            if (card.isActive()){
                card.setPosition(mouseX - card.getShape().getWidth() / 2,
                            mouseY - card.getShape().getHeight() / 2);
            }
        }
    }

    public final void draw(SpriteBatch batch) {
        energyBar.draw(batch);

        hand.draw(batch);

        checkCapacity();
    }

    public void updateWhenTouchCard(int mouseX, int mouseY) {
        for (NumberCard card : hand.getCards()) {
            if (card.isOnCard(mouseX, mouseY)) {
                playTouchedAnimation(card);
            } else
                card.initCard();
        }
    }

    private void playTouchedAnimation(NumberCard card){
        int movingDistance = 30;
        float speed = 3;

        if (card.getPoint().getY() < card.getInitPoint().getY() + movingDistance)
            card.setPosition(card.getPoint().getX(), card.getPoint().getY()+speed);
    }

    private void checkCapacity() {
        if (capacityManager.isFull()) {
            sumAccessor.reset();
            capacityManager.initialize();
            skillState = SkillState.NEUTRAL;
        }
    }

    public final void initHand() {
        for (NumberCard card : hand.getCards())
            card.initCard();
    }

    public final int getSum() {
        return sumAccessor.getRecord();
    }

    public final int getCondition() {
        return capacityManager.getCapacity();
    }

    public final void playCard() {
        if (hand.isCardActive()) {
            doWhenCardPlayed();

            energyBar.update();

            if (hand.isResettingCard())
                checkResettingCardPlay();
            else
                checkNormalCardPlayed();
        }
    }

    public void doWhenCardPlayed() {

    }

    private void checkNormalCardPlayed() {
        if (skillState == SkillState.READY)
            skillState = SkillState.NEUTRAL;

        sumAccessor.update(hand.getCardNumber());

        capacityManager.update();
    }

    private void checkResettingCardPlay() {
        if (isSkillReady()) {
            castSkill(character);
            energyBar.reset();
            skillState = SkillState.NEUTRAL;
        } else {
            doWhenResettingCardPlay();
        }
    }

    private boolean isSkillReady() {
        return skillState == SkillState.READY &&
                energyBar.isMaxEnergy();
    }

    public void castSkill(CharacterList character) {
    }

    private void doWhenResettingCardPlay() {
        //when resetting card played means sum reset, then add a number to sum.
        sumAccessor.reset();
        sumAccessor.update(hand.getCardNumber());
        //playing resetting card count as a card, thus capacityManager initializes then update.
        capacityManager.initialize();
        capacityManager.update();
        skillState = SkillState.READY;
    }

    public final void dispose() {
        hand.dispose();
        energyBar.dispose();
    }

    public final int getEnergy() {
        return energyBar.getEnergy();
    }
}

class CapacityManager {
    private final static int initCapacity = 7;
    private int capacity = initCapacity;

    public int getCapacity() {
        return capacity;
    }

    public void update() {
        capacity--;
    }

    public void initialize() {
        capacity = initCapacity;
    }

    public boolean isFull() {
        return capacity == 0;
    }
}
