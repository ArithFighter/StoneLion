package com.arithfighter.ccg;

import com.arithfighter.ccg.component.*;
import com.arithfighter.ccg.number.RandomNumArrayGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent implements WindowSetting {
    Hand hand;
    Table table;
    SumDisplacer sumDisplacer;
    NumberBox numberBox;
    NumberBox[] numberBoxes;
    RandomNumArrayGenerator randomNumArrayGenerator;
    NumberListInspector numberListInspector;
    int[] numbers;
    NumberBoxPlacer numberBoxPlacer;

    public GameComponent(Texture[] textures, Texture[] cards) {
        numberBoxPlacer = new NumberBoxPlacer(GRID_X*9.5f,GRID_Y*5, GRID_X);

        hand = new Hand(cards, CharacterList.KNIGHT);

        table = new Table(textures[1], CENTER_X+GRID_X*4, GRID_Y * 6);

        sumDisplacer = new SumDisplacer(textures[2], CENTER_X + GRID_X * 8, GRID_Y*7);

        numberBox = new NumberBox(textures[3], 300, 350);

        int numberBoxQuantity = 9;

        numberBoxes = new NumberBox[numberBoxQuantity];

        for (int i = 0; i< numberBoxQuantity; i++){
            numberBoxes[i] = new NumberBox(textures[3],
                    numberBoxPlacer.getNumberBoxX(i, numberBox.getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBox.getHeight()));
        }

        numbers = new int[numberBoxQuantity];

        randomNumArrayGenerator = new RandomNumArrayGenerator();

        numberListInspector = new NumberListInspector();
    }

    public void draw(SpriteBatch batch, int sum , int condition, int mouseX, int mouseY){
        table.draw(batch);

        sumDisplacer.draw(sum, condition, batch);

        drawNumbers(batch);

        drawHand(batch, mouseX, mouseY);
        
    }

    public void update(int sum){
        updateNumbers();

        handleWhenNumMatchedSum(sum);

        checkEveryNumMatched();
    }

    public void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (sum == numbers[i]) {
                getScoreAndSetNumToZero(i);
            }
        }
    }

    private void getScoreAndSetNumToZero(int i){
        if (numbers[i] > 0) {
            updateScore();
            randomNumArrayGenerator.setNumberInListToZero(i);
        }
    }

    public void updateScore() {}

    public void checkEveryNumMatched(){
        numberListInspector.inspectNumberList(numbers);

        if (numberListInspector.isAllNumberAreZero()){
            randomNumArrayGenerator.clear();
        }
    }

    public void updateNumbers(){
        System.arraycopy(randomNumArrayGenerator.getNumbers(), 0, this.numbers, 0, this.numbers.length);
    }

    public Hand getHand() {
        return hand;
    }

    public final void whenPlayCardOnTable(int mouseX, int mouseY){
        if (table.isOnDesk(mouseX, mouseY)){
            if (hand.isCardActive()){
                doWhenCardPlayed();

                if (hand.isResetCard())
                    doWhenResetCardPlay();
            }
        }
        hand.resetHand();
    }

    public void doWhenResetCardPlay() {}

    public void doWhenCardPlayed() {}

    private void drawNumbers(SpriteBatch batch) {
        for (int i = 0; i<numberBoxes.length;i++){
            if (numbers[i]>0)
                numberBoxes[i].draw(numbers[i], batch);
        }
    }

    private void drawHand(SpriteBatch batch, int mouseX, int mouseY){
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);
    }

    public void dispose() {
        sumDisplacer.dispose();
        numberBox.dispose();

        for (NumberBox numberBox:numberBoxes)
            numberBox.dispose();
    }
}
