package com.arithfighter.ccg.component;

import com.arithfighter.ccg.randomnum.RandomNumListGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
import com.arithfighter.ccg.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import static com.arithfighter.ccg.WindowSetting.GRID_X;
import static com.arithfighter.ccg.WindowSetting.GRID_Y;

public class NumberBoxDisplacer {
    private final NumberBox[] numberBoxes;
    private final int numberBoxQuantity = 9;
    private final int[] numbers;
    private final RandomNumListGenerator randomNumListGenerator;
    private final LinkedList<Integer> numberList = new LinkedList<>();

    public NumberBoxDisplacer(Texture texture) {
        numberBoxes = new NumberBox[numberBoxQuantity];

        createNumberBoxes(texture);

        numbers = new int[numberBoxQuantity];

        randomNumListGenerator = new RandomNumListGenerator();
    }

    public int getNumberBoxQuantity(){
        return numberBoxQuantity;
    }

    private void createNumberBoxes(Texture texture){
        float initX = GRID_X * 9.5f;
        float initY = GRID_Y * 5;
        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer(initX, initY, GRID_X);

        for (int i = 0; i < numberBoxQuantity; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public LinkedList<Integer> getNumberList(){
        return numberList;
    }

    public void set(int index, int value){
        if (index>numberBoxQuantity)
            index = numberBoxQuantity;
        if (index<0)
            index = 0;

        numberList.set(index, value);
    }

    public void update(int sum) {
        updateNumbers();

        handleWhenNumMatchedSum(sum);

        checkEveryNumMatched();
    }

    private void updateNumbers() {
        numberList.addAll(randomNumListGenerator.getNumbers());

        for (int i = 0; i < numberBoxQuantity; i++)
            numbers[i] = numberList.get(i);
    }

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (sum == numbers[i] && numbers[i] > 0) {
                doWhenSumAndNumMatched();
                set(i, 0);
            }
        }
    }

    public void doWhenSumAndNumMatched() {
    }

    private void checkEveryNumMatched() {
        NumberListInspector numberListInspector = new NumberListInspector();

        numberListInspector.inspectNumberList(numbers);

        if (numberListInspector.isAllNumberAreZero()){
            randomNumListGenerator.clear();
            numberList.clear();
        }

    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < numberBoxQuantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(numbers[i], batch);
        }
    }

    public void dispose() {
        for (NumberBox numberBox : numberBoxes)
            numberBox.dispose();
    }
}
