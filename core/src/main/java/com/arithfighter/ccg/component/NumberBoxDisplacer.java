package com.arithfighter.ccg.component;

import com.arithfighter.ccg.number.RandomNumArrayGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
import com.arithfighter.ccg.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.GRID_X;
import static com.arithfighter.ccg.WindowSetting.GRID_Y;

public class NumberBoxDisplacer {
    NumberBox numberBox;
    NumberBox[] numberBoxes;
    NumberBoxPlacer numberBoxPlacer;
    private final int numberBoxQuantity = 9;
    int[] numbers;
    RandomNumArrayGenerator randomNumArrayGenerator;
    NumberListInspector numberListInspector;

    public NumberBoxDisplacer(Texture[] textures) {
        numberBoxPlacer = new NumberBoxPlacer(GRID_X * 9.5f, GRID_Y * 5, GRID_X);

        numberBox = new NumberBox(textures[3], 300, 350);

        numberBoxes = new NumberBox[numberBoxQuantity];

        for (int i = 0; i < numberBoxQuantity; i++) {
            numberBoxes[i] = new NumberBox(textures[3],
                    numberBoxPlacer.getNumberBoxX(i, numberBox.getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBox.getHeight()));
        }

        numbers = new int[numberBoxQuantity];

        randomNumArrayGenerator = new RandomNumArrayGenerator();

        numberListInspector = new NumberListInspector();
    }

    public void update(int sum) {
        updateNumbers();

        handleWhenNumMatchedSum(sum);

        checkEveryNumMatched();
    }

    private void updateNumbers() {
        System.arraycopy(randomNumArrayGenerator.getNumbers(), 0, this.numbers, 0, this.numbers.length);
    }

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (isSumAndNumMatched(numbers[i], sum))
                randomNumArrayGenerator.setNumberInListToZero(i);
        }
    }

    private boolean isSumAndNumMatched(int number, int sum) {
        return sum == number && number > 0;
    }

    private void checkEveryNumMatched() {
        numberListInspector.inspectNumberList(numbers);

        if (numberListInspector.isAllNumberAreZero())
            randomNumArrayGenerator.clear();
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < numberBoxQuantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(numbers[i], batch);
        }
    }

    public void dispose() {
        numberBox.dispose();

        for (NumberBox numberBox : numberBoxes)
            numberBox.dispose();
    }
}
