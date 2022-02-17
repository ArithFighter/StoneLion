package com.arithfighter.ccg;

import com.arithfighter.ccg.component.*;
import com.arithfighter.ccg.system.NumberListInspector;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class GameComponent {
    Player player;
    Table table;
    SumDisplacer sumDisplacer;
    NumberListInspector numberListInspector;
    NumberBoxDisplacer numberBoxDisplacer;
    enum SkillFlag {NEUTRAL, READY}
    SkillFlag skillFlag = SkillFlag.NEUTRAL;

    public GameComponent(Texture[] textures, Texture[] cards, CharacterList character) {
        player = new Player(cards, character);

        table = new Table(textures[1], CENTER_X + GRID_X * 4, GRID_Y * 6);

        sumDisplacer = new SumDisplacer(textures[2], CENTER_X + GRID_X * 8, GRID_Y * 7);

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void checkNumberTier(int i) {
                int[] numbers = numberBoxDisplacer.getNumbers();
                int tier1 = 10;
                int tier2 = 26;

                if (numbers[i] < tier1)
                    updateScore1();
                if (numbers[i] >= tier1 && numbers[i] < tier2)
                    updateScore2();
                if (numbers[i] >= tier2)
                    updateScore3();
            }
        };

        numberListInspector = new NumberListInspector();
    }

    public void draw(SpriteBatch batch, int sum, int condition, int mouseX, int mouseY) {
        table.draw(batch);

        sumDisplacer.draw(sum, condition, batch);

        numberBoxDisplacer.draw(batch);

        drawHand(batch, mouseX, mouseY);
    }

    public void update(int sum) {
        numberBoxDisplacer.update(sum);

        checkEveryNumMatched();
    }

    public void updateScore1() {
    }

    public void updateScore2() {
    }

    public void updateScore3() {
    }

    private void checkEveryNumMatched() {
        numberListInspector.inspectNumberList(numberBoxDisplacer.getNumbers());

        if (numberListInspector.isAllNumberAreZero()) {
            numberBoxDisplacer.refreshNumbers();
        }
    }

    public Player getHand() {
        return player;
    }

    public final void whenPlayCardOnTable(int mouseX, int mouseY) {
        if (table.isOnTable(mouseX, mouseY)) {
            if (player.isCardActive()) {
                handlePlayingCard();
            }
        }
        player.resetHand();
    }

    private void handlePlayingCard() {
        if (player.isResetCard())
            checkResetCardPlay();
        else {
            skillFlag = SkillFlag.NEUTRAL;
            doWhenCardPlayed();
        }
    }

    private void checkResetCardPlay() {
        if (skillFlag == SkillFlag.READY) {
            activeSkill();
            skillFlag = SkillFlag.NEUTRAL;
        } else {
            doWhenResetCardPlay();
            skillFlag = SkillFlag.READY;
        }
    }

    public void activeSkill() {
    }

    public void doWhenResetCardPlay() {
    }

    public void doWhenCardPlayed() {
    }

    private void drawHand(SpriteBatch batch, int mouseX, int mouseY) {
        player.draw(batch);
        player.checkTouchingCard(mouseX, mouseY);
    }

    public void dispose() {
        sumDisplacer.dispose();

        numberBoxDisplacer.dispose();

        player.dispose();
    }
}
