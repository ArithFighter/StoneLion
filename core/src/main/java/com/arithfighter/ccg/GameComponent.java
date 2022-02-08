package com.arithfighter.ccg;

import com.arithfighter.ccg.component.NumberBoxPlacer;
import com.arithfighter.ccg.component.NumberBox;
import com.arithfighter.ccg.component.SumDisplacer;
import com.arithfighter.ccg.component.Table;
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
    NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer(GRID_X*9.5f,GRID_Y*5, GRID_X);

    public GameComponent(Texture[] textures) {
        hand = new Hand(textures[0], CharacterList.HUNTER);

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

    public void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (isNumAndSumMatched(i, sum)) {
                getScoreAndSetNumToZero(i);
            }
        }
    }

    private boolean isNumAndSumMatched(int index, int sum){
        return sum == numbers[index];
    }

    private void getScoreAndSetNumToZero(int i){
        if (numbers[i] > 0) {
            updateScore();
            setNumberInListToZero(i);
        }
    }

    public void updateScore() {
    }

    public void setNumberInListToZero(int i){
        randomNumArrayGenerator.setNumberInListToZero(i);
    }

    public void clearNumbers(){
        randomNumArrayGenerator.clear();
    }

    public void checkEveryNumMatched(){
        numberListInspector.inspectNumberList(numbers);

        if (numberListInspector.isAllNumberAreZero()){
            clearNumbers();
        }
    }

    public int[] getNumbers(){
        return numbers;
    }

    public void updateNumbers(){
        System.arraycopy(randomNumArrayGenerator.getNumbers(), 0, this.numbers, 0, this.numbers.length);
    }

    public Hand getHand() {
        return hand;
    }

    public final void whenPlayCardOnTable(int mouseX, int mouseY){
        if (table.isOnDesk(mouseX, mouseY)){
            if (hand.isCardActive())
                doWhenCardPlayed();
        }
        hand.resetHand();
    }

    public void doWhenCardPlayed() {}

    public void drawTableAndNumbers(SpriteBatch batch, int sum) {
        table.draw(batch);

        for (int i = 0; i<numberBoxes.length;i++)
            if (numbers[i]>0){
                numberBoxes[i].draw(numbers[i], batch);
            }

        sumDisplacer.draw(sum, batch);
    }

    public void drawHand(SpriteBatch batch, int mouseX, int mouseY){
        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);
    }

    public void dispose() {
        hand.dispose();
        sumDisplacer.dispose();
        numberBox.dispose();
        for (NumberBox numberBox:numberBoxes)
            numberBox.dispose();
    }
}
