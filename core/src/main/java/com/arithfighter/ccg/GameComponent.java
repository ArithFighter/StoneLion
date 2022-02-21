package com.arithfighter.ccg;

import com.arithfighter.ccg.component.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent {
    Player player;
    CardTable cardTable;
    enum SkillFlag {NEUTRAL, READY}
    SkillFlag skillFlag = SkillFlag.NEUTRAL;

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
    }

    public void draw(SpriteBatch batch, int sum, int condition, int mouseX, int mouseY) {
        cardTable.draw(batch, sum, condition);

        drawPlayer(batch, mouseX, mouseY);
    }

    public void update(int sum) {
        cardTable.update(sum);
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

    public final void whenPlayCardOnTable(int mouseX, int mouseY) {
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

    private void drawPlayer(SpriteBatch batch, int mouseX, int mouseY) {
        player.draw(batch);
        player.checkTouchingCard(mouseX, mouseY);
    }

    public void dispose() {
        cardTable.dispose();

        player.dispose();
    }
}
