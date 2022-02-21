package com.arithfighter.ccg;

import com.arithfighter.ccg.accessor.SumAccessor;
import com.arithfighter.ccg.component.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent {
    private final Player player;
    private final CardTable cardTable;
    private enum SkillFlag {NEUTRAL, READY}
    private SkillFlag skillFlag = SkillFlag.NEUTRAL;
    private final AutoResetHandler autoResetHandler;
    private final SumAccessor sumAccessor;

    public GameComponent(Texture[] textures, Texture[] cards, CharacterList character) {
        player = new Player(cards, character);

        cardTable = new CardTable(textures){
            @Override
            public void updateScore1() {
                getScore1();
            }

            @Override
            public void updateScore2() {
                getScore2();
            }

            @Override
            public void updateScore3() {
                getScore3();
            }
        };

        autoResetHandler = new AutoResetHandler();

        sumAccessor = new SumAccessor();
    }

    public void draw(SpriteBatch batch, int mouseX, int mouseY) {
        cardTable.draw(batch, sumAccessor.getSum(), autoResetHandler.getCondition());

        drawPlayer(batch, mouseX, mouseY);
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

    public void getScore1() {
    }

    public void getScore2() {
    }

    public void getScore3() {
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

    public void dispose() {
        cardTable.dispose();

        player.dispose();
    }
}
