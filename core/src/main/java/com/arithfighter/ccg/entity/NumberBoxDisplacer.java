package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.animate.Animator;
import com.arithfighter.ccg.pojo.GameNumProducer;
import com.arithfighter.ccg.time.TimeHandler;
import com.arithfighter.ccg.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class NumberBoxDisplacer {
    private final NumberBoxDrawer drawer;
    private final int maxQuantity;
    private final int[] numbers;
    private final RandomNumListGenerator randomNumListGenerator;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final NumberBoxAnimation animation;

    public NumberBoxDisplacer(Texture texture) {
        drawer = new NumberBoxDrawer(texture);
        
        maxQuantity = drawer.getMaxQuantity();
        
        numbers = new int[maxQuantity];

        randomNumListGenerator = new RandomNumListGenerator(maxQuantity);

        animation = new NumberBoxAnimation(drawer.getNumberBoxes());
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void refresh() {
        randomNumListGenerator.clear();
        numberList.clear();
    }

    public int getNumberBoxValue(int index) {
        return numberList.get(index);
    }

    public void set(int index, int value) {
        if (index>=0){
            int i = Math.min(index, maxQuantity);
            numberList.set(i, value);
        }
    }

    public void update(int sum) {
        updateNumbers();

        handleWhenNumMatchedSum(sum);

        checkEveryNumMatched();

        animation.setNumbers(numbers);
    }

    public void setBoxQuantity(int boxQuantity) {
        int quantity = Math.min(boxQuantity, maxQuantity);

        if (numberList.size() > 0)
            for (int i = 0; i < maxQuantity - quantity; i++)
                set(i, 0);
    }

    private void updateNumbers() {
        if (numberList.size() < maxQuantity)
            numberList.addAll(randomNumListGenerator.getNumbers());

        for (int i = 0; i < maxQuantity; i++)
            numbers[i] = numberList.get(i);
    }

    private void handleWhenNumMatchedSum(int sum) {
        for (int i = 0; i < numbers.length; i++) {
            if (sum == numbers[i] && numbers[i] > 0 && numberList.size() > 0) {
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
        drawer.draw(batch, numbers);

        animation.setBatch(batch);
        animation.draw();
    }

    public void dispose() {
        drawer.dispose();
    }
}

class RandomNumListGenerator {
    private final int maxQuantity;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final HashSet<Integer> numberSet = new HashSet<>();

    public RandomNumListGenerator(int maxQuantity){
        this.maxQuantity = maxQuantity;
    }

    public void clear(){
        numberList.clear();
        numberSet.clear();
    }

    public List<Integer> getNumbers() {
        addNumbersToList();

        return numberList;
    }

    private void addNumbersToList() {
        if (numberList.size() < maxQuantity) {
            addNumberUntilEqualToQuantity(maxQuantity);

            numberList.addAll(numberSet);
        }
    }

    private void addNumberUntilEqualToQuantity(int quantity) {
        addRandomNumberToSet(quantity);

        while (numberSet.size() < quantity)
            addNumberUntilEqualToQuantity(quantity - numberSet.size());
    }

    private void addRandomNumberToSet(int quantity) {
        GameNumProducer rnp = new GameNumProducer();

        for (int i = 0; i < quantity; i++)
            numberSet.add(rnp.getRandomNum());
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
        sumOfNumInspector =-1;
    }

    private void checkEveryNumInListAreZero(int[] numberList) {
        for (int number : numberList){
            sumOfNumInspector += number;
        }
        allNumAreZero = sumOfNumInspector == -1;
    }

    public boolean isAllNumberAreZero(){
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
