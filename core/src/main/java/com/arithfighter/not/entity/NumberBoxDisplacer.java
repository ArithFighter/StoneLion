package com.arithfighter.not.entity;

import com.arithfighter.not.animate.Animator;
import com.arithfighter.not.pojo.GameNumProducer;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.Mask;
import com.arithfighter.not.widget.NumberBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static com.arithfighter.not.WindowSetting.GRID_X;
import static com.arithfighter.not.WindowSetting.GRID_Y;

public class NumberBoxDisplacer {
    private final NumberBoxProducer numberBoxProducer;
    private final int maxQuantity;
    private final int[] numbers;
    private final RandomNumListGenerator randomNumListGenerator;
    private final LinkedList<Integer> numberList = new LinkedList<>();
    private final NumberBoxAnimation animation;
    private final MaskAnimation maskAnimation;

    public NumberBoxDisplacer(Texture[] textures) {
        numberBoxProducer = new NumberBoxProducer(textures[3]);
        
        maxQuantity = numberBoxProducer.getMaxQuantity();
        
        numbers = new int[maxQuantity];

        NumberBoxPlacer placer = new NumberBoxPlacer();

        Mask[] masks = new Mask[maxQuantity];
        for (int i = 0; i< maxQuantity;i++){
            masks[i] = new Mask(textures[5], 2.4f);
            masks[i].setPosition(
                    placer.getNumberBoxX(i, masks[i].getWidth()),
                    placer.getNumberBoxY(i, masks[i].getHeight()));
        }

        randomNumListGenerator = new RandomNumListGenerator(maxQuantity);

        animation = new NumberBoxAnimation(numberBoxProducer.getNumberBoxes());

        maskAnimation = new MaskAnimation(masks);
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void init() {
        randomNumListGenerator.clear();
        numberList.clear();
        maskAnimation.init();
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
            init();
        }
    }

    public void draw(SpriteBatch batch) {
        numberBoxProducer.draw(batch, numbers);

        animation.setBatch(batch);
        animation.draw();

        maskAnimation.draw(batch, 0.1f);
    }

    public void dispose() {
        numberBoxProducer.dispose();
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
        GameNumProducer gnp = new GameNumProducer();

        for (int i = 0; i < quantity; i++)
            numberSet.add(gnp.getRandomNum());
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

class NumberBoxProducer {
    private final NumberBox[] numberBoxes;
    private final static int maxQuantity = 9;

    public NumberBoxProducer(Texture texture){
        numberBoxes = new NumberBox[maxQuantity];

        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer();

        for (int i = 0; i < maxQuantity; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public int getMaxQuantity(){
        return maxQuantity;
    }

    public NumberBox[] getNumberBoxes(){
        return numberBoxes;
    }

    public void draw(SpriteBatch batch, int[] numbers){
        for (int i = 0; i < maxQuantity; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(batch, numbers[i]);
        }
    }

    public void dispose(){
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
