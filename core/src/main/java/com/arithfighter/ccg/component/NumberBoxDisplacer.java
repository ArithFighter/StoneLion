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
    NumberBoxPlacer numberBoxPlacer;
    private final int numberBoxQuantity = 9;
    int[] numbers;
    RandomNumListGenerator randomNumListGenerator;
    NumberListInspector numberListInspector;
    LinkedList<Integer> numberList = new LinkedList<>();

    public NumberBoxDisplacer(Texture texture) {
        numberBoxes = new NumberBox[numberBoxQuantity];

        createNumberBoxes(texture);

        numbers = new int[numberBoxQuantity];

        randomNumListGenerator = new RandomNumListGenerator();

        numberListInspector = new NumberListInspector();
    }

    private void createNumberBoxes(Texture texture){
        numberBoxPlacer = new NumberBoxPlacer(GRID_X * 9.5f, GRID_Y * 5, GRID_X);
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
                checkNumberTier(i);
                set(i, 0);
            }
        }
    }

    private void checkNumberTier(int i) {
        int tier1 = 10;
        int tier2 = 26;

        if (numbers[i] < tier1)
            getTier1();
        if (numbers[i] >= tier1 && numbers[i] < tier2)
            getTier2();
        if (numbers[i] >= tier2)
            getTier3();
    }

    public void getTier1() {
    }

    public void getTier2() {
    }

    public void getTier3() {
    }

    private boolean isSumAndNumMatched(int number, int sum) {
        return sum == number && number > 0;
    }

    private void checkEveryNumMatched() {
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
