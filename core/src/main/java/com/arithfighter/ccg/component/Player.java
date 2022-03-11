package com.arithfighter.ccg.component;

import com.arithfighter.ccg.accessor.SumAccessor;
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
    private final SumAccessor sumAccessor;
    private final EnergyBar energyBar;

    public Player(Texture[] textures, Texture[] cards, CharacterList character) {
        skillHandler = new SkillHandler();

        hand = new Hand(cards, character);

        skillSign = new Font(32);

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new SumAccessor();

        energyBar = new EnergyBar(textures);
    }

    public void draw(SpriteBatch batch, int mouseX, int mouseY, int energy) {
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        energyBar.draw(batch, energy);

        if (skillHandler.isSkillActive())//show font when skill is active
            skillSign.draw(batch, "Super", 100, 300);
    }

    public final void playCard(int mouseX, int mouseY, int energy) {
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
            sumAccessor.updateSum(hand.getCardNumber());
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
        sumAccessor.resetSum();
        sumAccessor.updateSum(hand.getCardNumber());
        autoResetHandler.update();
    }

    public void dispose() {
        hand.dispose();
        skillSign.dispose();
        energyBar.dispose();
    }
}
