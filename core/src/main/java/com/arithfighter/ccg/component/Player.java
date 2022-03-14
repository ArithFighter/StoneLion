package com.arithfighter.ccg.component;

import com.arithfighter.ccg.accessor.Recorder;
import com.arithfighter.ccg.character.CharacterList;
import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.system.AutoResetHandler;
import com.arithfighter.ccg.widget.EnergyBar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Hand hand;
    private final SkillHandler skillHandler;
    private final Font skillSign;
    private final AutoResetHandler autoResetHandler;
    private final Recorder sumAccessor;
    private final EnergyBar energyBar;

    public Player(Texture[] textures, Texture[] cards, CharacterList character) {
        skillHandler = new SkillHandler();

        hand = new Hand(cards, character);

        skillSign = new Font(32);

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new Recorder();

        energyBar = new EnergyBar(textures);
    }

    public void activateCard(int mouseX, int mouseY){
        hand.activateCard(mouseX, mouseY);
    }

    public void updateWhenDrag(int mouseX, int mouseY){
        hand.updateWhenDrag(mouseX, mouseY);
    }

    public void draw(SpriteBatch batch, int mouseX, int mouseY, int energy) {
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        energyBar.draw(batch, energy);

        checkAutoResetCondition();

        if (skillHandler.isSkillActive())//show font when skill is active
            skillSign.draw(batch, "Super", 100, 300);
    }

    private void checkAutoResetCondition() {
        if (autoResetHandler.isTimeToReset()) {
            sumAccessor.reset();
            autoResetHandler.initialize();
            skillHandler.init();
        }
    }

    public void initHand(){
        hand.initCardsPosition();
    }

    public int getSum(){
        return sumAccessor.getRecord();
    }

    public int getCondition(){
        return autoResetHandler.getCondition();
    }

    public final void playCard(int energy) {
        if (hand.isCardActive()) {
            doWhenCardPlayed();
            handlePlayingCard(energy);
        }
    }

    public void doWhenCardPlayed() {

    }

    private void handlePlayingCard(int energy) {
        if (hand.isResetCard())
            checkResetCardPlay(energy);
        else {
            if (skillHandler.isSkillReady()) {
                skillHandler.init();
            }
            sumAccessor.update(hand.getCardNumber());
            autoResetHandler.update();
        }
    }

    private void checkResetCardPlay(int energy) {
        if (skillHandler.isSkillReady() && isMaxEnergy(energy)) {
            skillHandler.setActive();
            activeSkill();
            autoResetHandler.initialize();
        } else {
            doWhenResetCardPlay();
            if (skillHandler.isSkillNeutral())
                skillHandler.setReady();
        }
    }

    public boolean isEnergyNotMax(int energy) {
        return energy < energyBar.getMax();
    }

    public boolean isMaxEnergy(int energy) {
        return energy == energyBar.getMax();
    }

    public void activeSkill() {

    }

    private void doWhenResetCardPlay() {
        sumAccessor.reset();
        sumAccessor.update(hand.getCardNumber());
        autoResetHandler.update();
    }

    public void dispose() {
        hand.dispose();
        skillSign.dispose();
        energyBar.dispose();
    }
}
