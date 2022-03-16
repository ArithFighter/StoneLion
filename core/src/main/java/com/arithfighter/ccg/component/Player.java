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

    public Player(Texture[] textures, Texture[] cards, CharacterList character) {
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

    public final void draw(SpriteBatch batch, int mouseX, int mouseY, int energy) {
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        energyBar.draw(batch, energy);

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

    public final void playCard(int energy) {
        if (hand.isCardActive()) {
            doWhenCardPlayed();

            if (energy < energyBar.getMax())
                updateEnergy();

            handlePlayingCard(energy);
        }
    }

    public void updateEnergy() {

    }

    public void doWhenCardPlayed() {

    }

    private void handlePlayingCard(int energy) {
        if (hand.isResetCard())
            checkResetCardPlay(energy);
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

    private void checkResetCardPlay(int energy) {
        if (skillHandler.isSkillReady() &&
                energy == energyBar.getMax()) {
            activeSkill();
            autoResetHandler.initialize();
            skillHandler.init();
        } else {
            doWhenResetCardPlay();
            if (skillHandler.isSkillNeutral())
                skillHandler.setReady();
        }
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
}
