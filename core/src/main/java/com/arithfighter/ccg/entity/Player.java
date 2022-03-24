package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.system.Recorder;
import com.arithfighter.ccg.widget.bar.EnergyBar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Hand hand;
    private final AutoResetHandler autoResetHandler;
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

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new Recorder();

        energyBar = new EnergyBar(textures);

        this.character = character;

        setEnergyGain(character);
    }

    private void setEnergyGain(CharacterList character){
        //Rogue gain more energy than other characters when play card
        if (character == CharacterList.ROGUE)
            energyGain = 4;
        else
            energyGain = 3;
    }

    public void init(){
        sumAccessor.reset();
        autoResetHandler.initialize();
        energyRecorder.reset();
    }

    public final void activateCard(int mouseX, int mouseY) {
        hand.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY) {
        hand.updateWhenDrag(mouseX, mouseY);
    }

    public final void draw(SpriteBatch batch) {
        energyBar.draw(batch, energyRecorder.getRecord());

        hand.draw(batch);

        checkAutoResetCondition();
    }

    public void checkCardIsTouched(int mouseX, int mouseY){
        hand.checkTouchingCard(mouseX, mouseY);
    }

    private void checkAutoResetCondition() {
        if (autoResetHandler.isTimeToReset()) {
            sumAccessor.reset();
            autoResetHandler.initialize();
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
        return autoResetHandler.getCondition();
    }

    public final void playCard() {
        if (hand.isCardActive()) {
            doWhenCardPlayed();

            if (energyRecorder.getRecord() < energyBar.getMax())
                energyRecorder.update(energyGain);

            if (hand.isResetCard())
                checkResetCardPlay();
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

        autoResetHandler.update();
    }

    private void checkResetCardPlay() {
        if (isSkillReady()) {
            castSkill(character);
            energyRecorder.reset();
            autoResetHandler.initialize();
            skillState = SkillState.NEUTRAL;
        } else {
            doWhenResetCardPlay();
        }
    }

    private boolean isSkillReady() {
        return skillState == SkillState.READY &&
                energyRecorder.getRecord() >= energyBar.getMax();
    }

    public void castSkill(CharacterList character) {
    }

    private void doWhenResetCardPlay() {
        sumAccessor.reset();
        sumAccessor.update(hand.getCardNumber());
        autoResetHandler.update();
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

class AutoResetHandler {
    private final static int initCondition = 6;
    private int condition = initCondition;

    public int getCondition(){
        return condition;
    }

    public void update(){
        condition--;
    }

    public void initialize(){
        condition = initCondition;
    }

    public boolean isTimeToReset(){
        return condition == 0;
    }
}
