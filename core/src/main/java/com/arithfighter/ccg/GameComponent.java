package com.arithfighter.ccg;

import com.arithfighter.ccg.component.*;
import com.arithfighter.ccg.number.RandomNumArrayGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class GameComponent {
    Hand hand;
    Table table;
    SumDisplacer sumDisplacer;
    RandomNumArrayGenerator randomNumArrayGenerator;
    NumberListInspector numberListInspector;
    int[] numbers;
    NumberBoxDisplacer numberBoxDisplacer;
    CardTexturesExtractor cardTexturesExtractor;
    enum SkillFlag {NEUTRAL, READY}
    SkillFlag skillFlag = SkillFlag.NEUTRAL;

    public GameComponent(Texture[] textures, Texture[] cards, CharacterList character) {
        cardTexturesExtractor = new CardTexturesExtractor(cards);

        hand = new Hand(cardTexturesExtractor.getCardSet(character), character);

        table = new Table(textures[1], CENTER_X + GRID_X * 4, GRID_Y * 6);

        sumDisplacer = new SumDisplacer(textures[2], CENTER_X + GRID_X * 8, GRID_Y * 7);

        numberBoxDisplacer = new NumberBoxDisplacer(textures);

        numbers = new int[numberBoxDisplacer.getNumberBoxQuantity()];

        randomNumArrayGenerator = new RandomNumArrayGenerator();

        numberListInspector = new NumberListInspector();
    }

    public void draw(SpriteBatch batch, int sum, int condition, int mouseX, int mouseY) {
        table.draw(batch);

        sumDisplacer.draw(sum, condition, batch);

        drawNumbers(batch);

        drawHand(batch, mouseX, mouseY);
    }

    public void update(int sum) {
        updateNumbers();

        handleWhenNumMatchedSum(sum);

        checkEveryNumMatched();
    }

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (sum == numbers[i]) {
                getScoreAndSetNumToZero(i);
            }
        }
    }

    private void getScoreAndSetNumToZero(int i) {
        if (numbers[i] > 0) {
            checkNumberTier(i);
            randomNumArrayGenerator.setNumberInListToZero(i);
        }
    }

    private void checkNumberTier(int i) {
        int tier1 = 10;
        int tier2 = 26;

        if (numbers[i] < tier1)
            updateScore1();
        if (numbers[i] >= tier1 && numbers[i] < tier2)
            updateScore2();
        if (numbers[i] >= tier2)
            updateScore3();
    }

    public void updateScore1() {
    }

    public void updateScore2() {
    }

    public void updateScore3() {
    }

    private void checkEveryNumMatched() {
        numberListInspector.inspectNumberList(numbers);

        if (numberListInspector.isAllNumberAreZero()) {
            randomNumArrayGenerator.clear();
        }
    }

    private void updateNumbers() {
        System.arraycopy(randomNumArrayGenerator.getNumbers(), 0, this.numbers, 0, this.numbers.length);
    }

    public Hand getHand() {
        return hand;
    }

    public final void whenPlayCardOnTable(int mouseX, int mouseY) {
        if (table.isOnTable(mouseX, mouseY)) {
            if (hand.isCardActive()) {
                handlePlayingCard();
            }
        }
        hand.resetHand();
    }

    private void handlePlayingCard(){
        if (hand.isResetCard())
            checkResetCardPlay();
        else{
            skillFlag = SkillFlag.NEUTRAL;
            doWhenCardPlayed();
        }
    }

    private void checkResetCardPlay() {
        if (skillFlag == SkillFlag.READY){
            activeSkill();
            skillFlag = SkillFlag.NEUTRAL;
        }
        else {
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

    private void drawNumbers(SpriteBatch batch) {
        for (int i = 0; i < numberBoxDisplacer.getNumberBoxes().length; i++) {
            if (numbers[i] > 0)
                numberBoxDisplacer.getNumberBoxes()[i].draw(numbers[i], batch);
        }
    }

    private void drawHand(SpriteBatch batch, int mouseX, int mouseY) {
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);
    }

    public void dispose() {
        sumDisplacer.dispose();

        numberBoxDisplacer.dispose();

        cardTexturesExtractor.dispose();
    }
}
