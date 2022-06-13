package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.animate.Animator;
import com.arithfighter.not.entity.MaskAnimation;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.system.GameNumProducer;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.system.RandomNumListProducer;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.Mask;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;
import java.util.List;

import static com.arithfighter.not.WindowSetting.GRID_X;
import static com.arithfighter.not.WindowSetting.GRID_Y;

public class NumberBoxEntity {
    private final NumberBoxProducer numberBoxProducer;
    private final int maxQuantity;
    private final int[] numbers;
    private final RandomNumListProducer randomNumListProducer;
    private final NumberBoxAnimation animation;
    private MaskAnimation maskAnimation;
    private final NumberListController numberListController;
    private final NumberListInspector numberListInspector = new NumberListInspector();

    public NumberBoxEntity(Texture[] textures, Font font) {
        numberBoxProducer = new NumberBoxProducer(textures[3], font);

        maxQuantity = numberBoxProducer.getMaxQuantity();

        numbers = new int[maxQuantity];

        NumberBoxPlacer placer = new NumberBoxPlacer();

        randomNumListProducer = new RandomNumListProducer(new GameNumProducer());
        randomNumListProducer.setMaxQuantity(maxQuantity);

        animation = new NumberBoxAnimation(numberBoxProducer.getNumberBoxes());

        createMaskAnimation(textures[5], placer);

        numberListController = new NumberListController(maxQuantity);
    }

    private void createMaskAnimation(Texture texture, NumberBoxPlacer placer) {
        Mask[] masks = new Mask[maxQuantity];
        for (int i = 0; i < maxQuantity; i++) {
            masks[i] = new Mask(texture, 2.4f);
            masks[i].setPosition(
                    placer.getNumberBoxX(i, masks[i].getWidth()),
                    placer.getNumberBoxY(i, masks[i].getHeight()));
        }

        maskAnimation = new MaskAnimation(masks);
    }

    public boolean isAllNumZero() {
        return numberListInspector.isAllNumberAreZero();
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void init() {
        randomNumListProducer.clear();
        maskAnimation.init();
        numberListController.init();
    }

    public int getNumberBoxValue(int index) {
        return numberListController.getNumberList().get(index);
    }

    public void set(int index, int value) {
        numberListController.set(index, value);
    }

    public void update(int sum) {
        updateNumberList();

        updateNumbers();

        handleWhenNumMatchedSum(sum);

        numberListInspector.inspectNumberList(numbers);

        animation.setNumbers(numbers);
    }

    public void setBoxQuantity(int boxQuantity) {
        numberListController.setBoxQuantity(boxQuantity);
    }

    private void updateNumberList() {
        LinkedList<Integer> numberList = numberListController.getNumberList();
        if (numberList.size() < maxQuantity)
            numberList.addAll(randomNumListProducer.getNumbers());
    }

    private void updateNumbers() {
        for (int i = 0; i < maxQuantity; i++)
            numbers[i] = numberListController.getNumberList().get(i);
    }

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (isNonZeroNumMatchedSum(i, sum)) {
                animation.setMatchedBoxIndex(i);

                doWhenSumAndNumMatched();

                set(i, 0);
            }
        }
    }

    private boolean isNonZeroNumMatchedSum(int i, int sum) {
        return sum == numbers[i] && numbers[i] > 0 && numberListController.getNumberList().size() > 0;
    }

    public void doWhenSumAndNumMatched() {
    }

    public void draw(SpriteBatch batch) {
        numberBoxProducer.draw(batch, numbers);

        animation.setBatch(batch);
        animation.draw();

        maskAnimation.draw(batch, 0.1f);
    }
}

class NumberListController {
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final RandomIndexPicker randomIndexPicker;
    private final int maxQuantity;

    public NumberListController(int maxQuantity) {
        this.maxQuantity = maxQuantity;
        randomIndexPicker = new RandomIndexPicker(maxQuantity);
    }

    public void init() {
        numberList.clear();
        randomIndexPicker.clear();
    }

    public void set(int index, int value) {
        if (index >= 0) {
            int i = Math.min(index, maxQuantity - 1);
            numberList.set(i, value);
        }
    }

    public void setBoxQuantity(int boxQuantity) {
        int quantity = Math.min(boxQuantity, maxQuantity);
        int zeroValueQuantity = maxQuantity - quantity;

        randomIndexPicker.setQuantity(zeroValueQuantity);

        if (numberList.size() >= maxQuantity) {
            for (int i = 0; i < zeroValueQuantity; i++)
                set(randomIndexPicker.getIndexes().get(i), 0);
        }
    }

    public LinkedList<Integer> getNumberList() {
        return numberList;
    }
}

class RandomIndexPicker {
    RandomNumProducer randomNumProducer;
    RandomNumListProducer randomNumListProducer;

    public RandomIndexPicker(int size) {
        randomNumProducer = new RandomNumProducer(size - 1, 0);
        randomNumListProducer = new RandomNumListProducer(randomNumProducer);
    }

    public void setQuantity(int quantity) {
        randomNumListProducer.setMaxQuantity(quantity);
    }

    public List<Integer> getIndexes() {
        return randomNumListProducer.getNumbers();
    }

    public void clear() {
        randomNumListProducer.clear();
    }
}

class NumberListInspector {
    private int sumOfNumInspector = -1;
    private boolean allNumAreZero = false;

    public final void inspectNumberList(int[] numberList) {
        checkEveryNumInListAreZero(numberList);
        resetInspector();
    }

    private void resetInspector() {
        sumOfNumInspector = -1;
    }

    private void checkEveryNumInListAreZero(int[] numberList) {
        for (int number : numberList) {
            sumOfNumInspector += number;
        }
        allNumAreZero = sumOfNumInspector == -1;
    }

    public boolean isAllNumberAreZero() {
        return allNumAreZero;
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

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public void setBatch(SpriteBatch batch) {
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

    private void init() {
        timeHandler.resetPastedTime();
        matchedBoxIndex -= matchedBoxIndex + 1;
    }
}

class NumberBoxProducer {
    private final NumberBox[] numberBoxes;
    private final static int maxQuantity = 9;

    public NumberBoxProducer(Texture texture, Font font) {
        numberBoxes = new NumberBox[maxQuantity];

        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer();

        for (int i = 0; i < maxQuantity; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setFont(font);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public NumberBox[] getNumberBoxes() {
        return numberBoxes;
    }

    public void draw(SpriteBatch batch, int[] numbers) {
        for (int i = 0; i < maxQuantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(batch, numbers[i]);
        }
    }
}

class NumberBoxPlacer {
    private final float initX = GRID_X * 7.5f;
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
