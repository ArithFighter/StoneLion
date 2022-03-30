package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.system.Recorder;
import com.arithfighter.ccg.widget.bar.EnergyBar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Hand hand;
    private final CardLoadingHandler cardLoadingHandler;
    private final Recorder sumAccessor;
    private final EnergyBar energyBar;
    private final CharacterList character;
    private final Recorder energyRecorder;
    private int energyGain;

    private enum SkillState {NEUTRAL, READY}

    private SkillState skillState = SkillState.NEUTRAL;

    public Player(Texture[] textures, Texture[] cards, CharacterList character) {
        energyRecorder = new Recorder();

        hand = new Hand(cards, character);

        cardLoadingHandler = new CardLoadingHandler();

        sumAccessor = new Recorder();

        energyBar = new EnergyBar(textures);

        this.character = character;

        setEnergyGain(character);
    }

    private void setEnergyGain(CharacterList character) {
        //Rogue gain more energy than other characters when play card
        if (character == CharacterList.ROGUE)
            energyGain = 4;
        else
            energyGain = 3;
    }

    public void init() {
        sumAccessor.reset();
        cardLoadingHandler.initialize();
        energyRecorder.reset();
    }

    public final void activateCard(int mouseX, int mouseY) {
        hand.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY) {
        hand.updateWhenDrag(mouseX, mouseY);
    }

    public final void draw(SpriteBatch batch) {
        energyBar.setEnergy(energyRecorder.getRecord());
        energyBar.draw(batch);

        hand.draw(batch);

        checkAutoResetCondition();
    }

    public void checkCardIsTouched(int mouseX, int mouseY) {
        hand.checkTouchingCard(mouseX, mouseY);
    }

    private void checkAutoResetCondition() {
        if (cardLoadingHandler.isFull()) {
            sumAccessor.reset();
            cardLoadingHandler.initialize();
            skillState = SkillState.NEUTRAL;
        }
    }

    public final void initHand() {
        hand.initCardsPosition();
    }

    public final int getSum() {
        return sumAccessor.getRecord();
    }

    public final int getCondition() {
        return cardLoadingHandler.getCapacity();
    }

    public final void playCard() {
        if (hand.isCardActive()) {
            doWhenCardPlayed();

            if (energyBar.isNotFull())
                energyRecorder.update(energyGain);

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

        cardLoadingHandler.update();
    }

    private void checkResettingCardPlay() {
        if (isSkillReady()) {
            castSkill(character);
            energyRecorder.reset();
            cardLoadingHandler.initialize();
            skillState = SkillState.NEUTRAL;
        } else {
            doWhenResettingCardPlay();
        }
    }

    private boolean isSkillReady() {
        return skillState == SkillState.READY &&
                energyRecorder.getRecord() >= energyBar.getMax();
    }

    public void castSkill(CharacterList character) {
    }

    private void doWhenResettingCardPlay() {
        //when resetting card played means sum reset, then add a number to sum.
        sumAccessor.reset();
        sumAccessor.update(hand.getCardNumber());
        //playing resetting card count as a card, thus Auto-reset handler initializes then update.
        cardLoadingHandler.initialize();
        cardLoadingHandler.update();
        skillState = SkillState.READY;
    }

    public final void dispose() {
        hand.dispose();
        energyBar.dispose();
    }

    public final int getEnergy() {
        return energyRecorder.getRecord();
    }
}

class CardLoadingHandler {
    private final static int initCapacity = 6;
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
