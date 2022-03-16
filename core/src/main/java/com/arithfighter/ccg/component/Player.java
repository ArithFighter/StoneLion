package com.arithfighter.ccg.component;

import com.arithfighter.ccg.system.Recorder;
import com.arithfighter.ccg.system.AutoResetHandler;
import com.arithfighter.ccg.widget.EnergyBar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Hand hand;
    private final SkillHandler skillHandler;
    private final AutoResetHandler autoResetHandler;
    private final Recorder sumAccessor;
    private final EnergyBar energyBar;
    private final CharacterList character;
    private final Recorder energyRecorder;

    public Player(Texture[] textures, Texture[] cards, CharacterList character) {
        energyRecorder = new Recorder();

        skillHandler = new SkillHandler();

        hand = new Hand(cards, character);

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new Recorder();

        energyBar = new EnergyBar(textures);

        this.character = character;
    }

    public final CharacterList getCharacter(){
        return character;
    }

    public final void activateCard(int mouseX, int mouseY){
        hand.activateCard(mouseX, mouseY);
    }

    public final void updateWhenDrag(int mouseX, int mouseY){
        hand.updateWhenDrag(mouseX, mouseY);
    }

    public final void draw(SpriteBatch batch, int mouseX, int mouseY) {
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        energyBar.draw(batch, energyRecorder.getRecord());

        checkAutoResetCondition();
    }

    private void checkAutoResetCondition() {
        if (autoResetHandler.isTimeToReset()) {
            sumAccessor.reset();
            autoResetHandler.initialize();
            skillHandler.init();
        }
    }

    public final void initHand(){
        hand.initCardsPosition();
    }

    public final int getSum(){
        return sumAccessor.getRecord();
    }

    public final int getCondition(){
        return autoResetHandler.getCondition();
    }

    public final void playCard() {
        if (hand.isCardActive()) {
            doWhenCardPlayed();

            if (energyRecorder.getRecord() < energyBar.getMax())
                energyRecorder.update(3);

            handlePlayingCard();
        }
    }

    public void doWhenCardPlayed() {

    }

    private void handlePlayingCard() {
        if (hand.isResetCard())
            checkResetCardPlay();
        else {
            checkNormalCardPlayed();
        }
    }

    private void checkNormalCardPlayed(){
        if (skillHandler.isSkillReady())
            skillHandler.init();

        sumAccessor.update(hand.getCardNumber());

        autoResetHandler.update();
    }

    private void checkResetCardPlay() {
        if (isSkillReady()) {
            energyRecorder.reset();
            activeSkill();
            autoResetHandler.initialize();
            skillHandler.init();
        } else {
            doWhenResetCardPlay();
            skillHandler.setReady();
        }
    }

    private boolean isSkillReady(){
        return skillHandler.isSkillReady() &&
                energyRecorder.getRecord() == energyBar.getMax();
    }

    public void activeSkill() {
    }

    private void doWhenResetCardPlay() {
        sumAccessor.reset();
        sumAccessor.update(hand.getCardNumber());
        autoResetHandler.update();
    }

    public final void dispose() {
        hand.dispose();
        energyBar.dispose();
    }

    public int getEnergy() {
        return energyRecorder.getRecord();
    }
}
