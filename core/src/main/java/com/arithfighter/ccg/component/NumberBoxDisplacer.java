package com.arithfighter.ccg.component;

import com.arithfighter.ccg.number.RandomNumListGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
import com.arithfighter.ccg.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import static com.arithfighter.ccg.WindowSetting.GRID_X;
import static com.arithfighter.ccg.WindowSetting.GRID_Y;

public class NumberBoxDisplacer {
    NumberBox sample;
    NumberBox[] numberBoxes;
    private final int numberBoxQuantity = 9;
    int[] numbers;
    RandomNumListGenerator randomNumListGenerator;
    LinkedList<Integer> numberList = new LinkedList<>();

    public NumberBoxDisplacer(Texture texture) {
        numberBoxes = new NumberBox[numberBoxQuantity];

        createNumberBoxes(texture);

        numbers = new int[numberBoxQuantity];

        randomNumListGenerator = new RandomNumListGenerator();
    }

    private void createNumberBoxes(Texture texture){
        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer(
                GRID_X * 9.5f,
                GRID_Y * 5,
                GRID_X);

        sample = new NumberBox(texture, 300, 350);

        for (int i = 0; i < numberBoxQuantity; i++) {
            numberBoxes[i] = new NumberBox(texture,
                    numberBoxPlacer.getNumberBoxX(i, sample.getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, sample.getHeight()));
        }
    }

    public void set(int index, int value){
        if (index>numberBoxQuantity)
            index = numberBoxQuantity;

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
            if (isSumAndNumMatched(numbers[i], sum)) {
                doWhenSumAndNumMatched();
                set(i, 0);
            }
        }
    }

    public void doWhenSumAndNumMatched() {
    }

    private boolean isSumAndNumMatched(int number, int sum) {
        return sum == number && number > 0;
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
        sample.dispose();

        for (NumberBox numberBox : numberBoxes)
            numberBox.dispose();
    }
}
