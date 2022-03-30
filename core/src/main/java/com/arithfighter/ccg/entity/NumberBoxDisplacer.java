package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.animate.Animator;
import com.arithfighter.ccg.system.number.RandomNumListGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
import com.arithfighter.ccg.time.TimeHandler;
import com.arithfighter.ccg.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import static com.arithfighter.ccg.WindowSetting.GRID_X;
import static com.arithfighter.ccg.WindowSetting.GRID_Y;

public class NumberBoxDisplacer {
    private final NumberBox[] numberBoxes;
    private final static int BOX_QUANTITY = 9;
    private final int[] numbers = new int[BOX_QUANTITY];
    private final RandomNumListGenerator randomNumListGenerator;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final NumberBoxAnimation animation;

    public NumberBoxDisplacer(Texture texture) {
        numberBoxes = new NumberBox[BOX_QUANTITY];

        createNumberBoxes(texture);

        randomNumListGenerator = new RandomNumListGenerator(BOX_QUANTITY);

        animation = new NumberBoxAnimation(numberBoxes);
    }

    public int getNumberBoxQuantity() {
        return BOX_QUANTITY;
    }

    private void createNumberBoxes(Texture texture) {
        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer();

        for (int i = 0; i < BOX_QUANTITY; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public void refresh() {
        randomNumListGenerator.clear();
        numberList.clear();
    }

    public int getNumberBoxValue(int index) {
        return numberList.get(index);
    }

    public void set(int index, int value) {
        if (index > BOX_QUANTITY)
            index = BOX_QUANTITY;

        if (index < 0)
            index = 0;

        numberList.set(index, value);
    }

    public void update(int sum) {
        updateNumbers();

        handleWhenNumMatchedSum(sum);

        checkEveryNumMatched();

        animation.setNumbers(numbers);
    }

    private void updateNumbers() {
        if (numberList.size() < BOX_QUANTITY)
            numberList.addAll(randomNumListGenerator.getNumbers());

        for (int i = 0; i < BOX_QUANTITY; i++)
            numbers[i] = numberList.get(i);
    }

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (sum == numbers[i] && numbers[i] > 0) {
                animation.setMatchedBoxIndex(i);

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

        if (numberListInspector.isAllNumberAreZero()) {
            randomNumListGenerator.clear();
            numberList.clear();
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < BOX_QUANTITY; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(batch, numbers[i]);
        }

        animation.setBatch(batch);
        animation.draw();
    }

    public void dispose() {
        for (NumberBox numberBox : numberBoxes)
            numberBox.dispose();
    }
}

class NumberBoxPlacer {
    private final float initX = GRID_X * 9.5f;
    private final float initY = GRID_Y * 5;
    private final float margin = GRID_X;

    public float getNumberBoxX(int i, float width) {
        float x = initX;

        for (int j = 0; j < 3; j++)
            if (i % 3 == j) x += (margin + width) * j;

        return x;
    }

    public float getNumberBoxY(int i, float height) {
        float y = initY;

        for (int j = 0; j < 5; j++)
            if (i / 3 == j)
                y += (margin + height) * j;

        return y;
    }
}

class NumberBoxAnimation {
    private int[] numbers;
    private final Animator animator;
    private final TimeHandler timeHandler;
    private SpriteBatch batch;
    private int matchedBoxIndex = -1;

    public NumberBoxAnimation(NumberBox[] numberBoxes) {
        animator = new Animator() {
            @Override
            public void renderEffect() {
                numberBoxes[matchedBoxIndex].draw(batch, numbers[matchedBoxIndex]);
            }
        };
        timeHandler = new TimeHandler();
    }

    public void setNumbers(int[] numbers){
        this.numbers = numbers;
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }

    public void setMatchedBoxIndex(int i) {
        matchedBoxIndex = i;
    }

    public void draw() {
        int ratePerSec = 8;
        float durationSec = 1.2f;

        if (matchedBoxIndex >= 0) {
            timeHandler.updatePastedTime();

            animator.animateFlashy(ratePerSec);

            if (timeHandler.getPastedTime() > durationSec)
                init();
        }
    }

    private void init(){
        timeHandler.resetPastedTime();
        matchedBoxIndex -= matchedBoxIndex + 1;
    }
}
