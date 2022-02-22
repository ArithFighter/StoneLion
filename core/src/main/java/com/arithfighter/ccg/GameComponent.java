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
    private final NumberBoxDisplacer numberBoxDisplacer;

    public GameComponent(Texture[] textures, Texture[] cards, CharacterList character) {
        player = new Player(cards, character);

        cardTable = new CardTable(textures);

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new SumAccessor();

        energyBar = new EnergyBar(textures);

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void getTier1() {
                updateScore1();
            }

            @Override
            public void getTier2() {
                updateScore2();
            }

            @Override
            public void getTier3() {
                updateScore3();
            }
        };
    }

    public void updateScore1() {
    }

    public void updateScore2() {
    }

    public void updateScore3() {
    }

    public void draw(SpriteBatch batch, int mouseX, int mouseY, int energy) {
        cardTable.draw(batch, sumAccessor.getSum(), autoResetHandler.getCondition());

        energyBar.draw(batch, energy);

        numberBoxDisplacer.draw(batch);

        drawPlayer(batch, mouseX, mouseY);
    }

    public void update() {
        checkAutoResetCondition();

        numberBoxDisplacer.update(sumAccessor.getSum());
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
        if (cardTable.isCardOnBoard(mouseX, mouseY)) {
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
            autoResetHandler.initialize();
            skillFlag = SkillFlag.NEUTRAL;
        } else {
            doWhenResetCardPlay();
            skillFlag = SkillFlag.READY;
        }
    }

    public void activeSkill() {
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

    public boolean isEnergyNotMax(int energy) {
        return energy < energyBar.getMax();
    }

    public boolean isMaxEnergy(int energy) {
        return energy == energyBar.getMax();
    }

    public void dispose() {
        cardTable.dispose();
        player.dispose();
        energyBar.dispose();
        numberBoxDisplacer.dispose();
    }
}
