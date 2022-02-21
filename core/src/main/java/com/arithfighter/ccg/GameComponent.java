package com.arithfighter.ccg;

import com.arithfighter.ccg.accessor.SumAccessor;
import com.arithfighter.ccg.component.*;
import com.arithfighter.ccg.widget.EnergyBar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent {
    private final Player player;
    private final CardTable cardTable;
    private enum SkillFlag {NEUTRAL, READY}
    private SkillFlag skillFlag = SkillFlag.NEUTRAL;
    private final AutoResetHandler autoResetHandler;
    private final SumAccessor sumAccessor;
    private final EnergyBar energyBar;

    public GameComponent(Texture[] textures, Texture[] cards, CharacterList character) {
        player = new Player(cards, character);

        cardTable = new CardTable(textures);

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new SumAccessor();

        energyBar = new EnergyBar(textures);
    }

    public void draw(SpriteBatch batch, int mouseX, int mouseY, int energy) {
        cardTable.draw(batch, sumAccessor.getSum(), autoResetHandler.getCondition());

        drawPlayer(batch, mouseX, mouseY);

        energyBar.draw(batch, energy);
    }

    public void update() {
        cardTable.update(sumAccessor.getSum());

        checkAutoResetCondition();
    }

    private void checkAutoResetCondition() {
        if (autoResetHandler.isTimeToReset()) {
            sumAccessor.resetSum();
            autoResetHandler.initialize();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public final void playCardOnTable(int mouseX, int mouseY) {
        if (cardTable.isCardOnBoard(mouseX,mouseY)) {
            if (player.isCardActive()) {
                handlePlayingCard();
            }
        }
        player.initCardsPosition();
    }

    private void handlePlayingCard() {
        if (player.isResetCard())
            checkResetCardPlay();
        else {
            skillFlag = SkillFlag.NEUTRAL;
            doWhenCardPlayed();
            sumAccessor.updateSum(player.getCardNumber());
            autoResetHandler.update();
        }
    }

    private void checkResetCardPlay() {
        if (skillFlag == SkillFlag.READY) {
            activeSkill();
        } else {
            doWhenResetCardPlay();
            skillFlag = SkillFlag.READY;
        }
    }

    public void activeSkill() {
        autoResetHandler.initialize();
        skillFlag = SkillFlag.NEUTRAL;
    }

    private void doWhenResetCardPlay() {
        sumAccessor.resetSum();
        sumAccessor.updateSum(player.getCardNumber());
    }

    public void doWhenCardPlayed() {
    }

    private void drawPlayer(SpriteBatch batch, int mouseX, int mouseY) {
        player.draw(batch);
        player.checkTouchingCard(mouseX, mouseY);
    }

    public boolean isEnergyNotMax(int energy){
        return energy<energyBar.getMax();
    }

    public boolean isMaxEnergy(int energy){
        return energy==energyBar.getMax();
    }

    public void dispose() {
        cardTable.dispose();

        player.dispose();

        energyBar.dispose();
    }
}
