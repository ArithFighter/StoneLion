package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.entity.MaskAnimation;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.system.GameNumProducer;
import com.arithfighter.not.system.RandomNumListProducer;
import com.arithfighter.not.widget.a1.Mask;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

public class NumberBoxEntity {
    private final NumberBoxService numberBoxService;
    private final int maxQuantity;
    private final RandomNumListProducer randomNumListProducer;
    private final NumberBoxAnimation animation;
    private MaskAnimation maskAnimation;
    private final NumberListController numberListController;
    private final NumberListInspector numberListInspector = new NumberListInspector();

    public NumberBoxEntity(Texture[] textures, Font font) {
        NumberBoxPlacer placer = new NumberBoxPlacer();

        numberBoxService = new NumberBoxService(textures[3], font);
        numberBoxService.setPosition(placer);

        maxQuantity = numberBoxService.getQuantity();
        createMaskAnimation(textures[5], placer);

        randomNumListProducer = new RandomNumListProducer(new GameNumProducer());
        randomNumListProducer.setMaxQuantity(maxQuantity);

        animation = new NumberBoxAnimation(numberBoxService.getNumberBoxes());

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

        numberListController.updateNumbers();

        handleWhenNumMatchedSum(sum);

        int[] numbers = numberListController.getNumbers();

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

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numberListController.getNumbers().length; i++) {
            if (isNonZeroNumMatchedSum(i, sum)) {
                animation.setMatchedBoxIndex(i);

                doWhenSumAndNumMatched();

                set(i, 0);
            }
        }
    }

    private boolean isNonZeroNumMatchedSum(int i, int sum) {
        int[] numbers = numberListController.getNumbers();
        return sum == numbers[i] && numbers[i] > 0 && numberListController.getNumberList().size() > 0;
    }

    public void doWhenSumAndNumMatched() {
    }

    public void draw(SpriteBatch batch) {
        numberBoxService.draw(batch, numberListController.getNumbers());

        animation.setBatch(batch);
        animation.draw();

        maskAnimation.draw(batch, 0.1f);
    }
}

class NumberListController {
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final RandomIndexPicker randomIndexPicker;
    private final int[] numbers;
    private final int maxQuantity;

    public NumberListController(int maxQuantity) {
        this.maxQuantity = maxQuantity;
        randomIndexPicker = new RandomIndexPicker(maxQuantity);
        numbers = new int[maxQuantity];
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

    public void updateNumbers() {
        for (int i = 0; i < maxQuantity; i++)
            numbers[i] = numberList.get(i);
    }

    public int[] getNumbers() {
        return numbers;
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

