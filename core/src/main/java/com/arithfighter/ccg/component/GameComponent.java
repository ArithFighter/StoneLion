package com.arithfighter.ccg.component;

import com.arithfighter.ccg.system.AutoResetHandler;
import com.arithfighter.ccg.CharacterList;
import com.arithfighter.ccg.accessor.SumAccessor;
import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.widget.EnergyBar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent {
    private final Player player;
    private final CardTable cardTable;
    private final SkillHandler skillHandler;
    private final AutoResetHandler autoResetHandler;
    private final SumAccessor sumAccessor;
    private final EnergyBar energyBar;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private final Font skillSign;

    public GameComponent(Texture[] textures, Texture[] cards, CharacterList character) {
        skillHandler = new SkillHandler();

        player = new Player(cards, character);

        cardTable = new CardTable(textures);

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new SumAccessor();

        energyBar = new EnergyBar(textures);

        numberBoxDisplacer = new NumberBoxDisplacer(textures[3]) {
            @Override
            public void doWhenSumAndNumMatched() {
                getScore();
            }
        };

        skillSign = new Font(32);
    }

    public void getScore(){
    }

    public void draw(SpriteBatch batch, int mouseX, int mouseY, int energy) {
        cardTable.draw(batch, sumAccessor.getSum(), autoResetHandler.getCondition());

        energyBar.draw(batch, energy);

        numberBoxDisplacer.draw(batch);

        player.draw(batch);
        player.checkTouchingCard(mouseX, mouseY);

        if (skillHandler.isSkillActive())
            skillSign.draw(batch, "Super",100, 300);
    }

    public void update() {
        numberBoxDisplacer.update(sumAccessor.getSum());//this always on top of stack

        checkAutoResetCondition();
    }

    private void checkAutoResetCondition() {
        if (autoResetHandler.isTimeToReset()) {
            sumAccessor.resetSum();
            autoResetHandler.initialize();
            skillHandler.init();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public final void playCardOnTable(int mouseX, int mouseY, int energy) {
        if (cardTable.isCardOnBoard(mouseX, mouseY)) {
            if (player.isCardActive()) {
                doWhenCardPlayed();
                handlePlayingCard(energy);
            }
        }
        player.initCardsPosition();
    }

    public void doWhenCardPlayed() {
    }

    private void handlePlayingCard(int energy) {
        if (player.isResetCard())
            checkResetCardPlay(energy);
        else {
            if (skillHandler.isSkillReady()){
                skillHandler.init();
            }
            sumAccessor.updateSum(player.getCardNumber());
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

    public void activeSkill() {
    }

    private void doWhenResetCardPlay() {
        sumAccessor.resetSum();
        sumAccessor.updateSum(player.getCardNumber());
        autoResetHandler.update();
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
        skillSign.dispose();
    }
}
