package com.arithfighter.ccg.entity.player;

import com.arithfighter.ccg.pojo.Recorder;
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
        hand.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY) {
        hand.updateWhenDrag(mouseX, mouseY);
    }

    public final void draw(SpriteBatch batch) {
        energyBar.draw(batch);

        hand.draw(batch);

        checkCapacity();
    }

    public void updateWhenTouchCard(int mouseX, int mouseY) {
        hand.updateWhenTouchCard(mouseX, mouseY);
    }

    private void checkCapacity() {
        if (capacityManager.isFull()) {
            sumAccessor.reset();
            capacityManager.initialize();
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
        //playing resetting card count as a card, thus Auto-reset handler initializes then update.
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
