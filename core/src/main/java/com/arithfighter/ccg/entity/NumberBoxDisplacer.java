package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.system.number.RandomNumListGenerator;
import com.arithfighter.ccg.system.NumberListInspector;
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

    public NumberBoxDisplacer(Texture texture) {
        numberBoxes = new NumberBox[BOX_QUANTITY];

        createNumberBoxes(texture);

        randomNumListGenerator = new RandomNumListGenerator(BOX_QUANTITY);
    }

    public int getNumberBoxQuantity(){
        return BOX_QUANTITY;
    }

    private void createNumberBoxes(Texture texture){
        NumberBoxPlacer numberBoxPlacer = new NumberBoxPlacer();

        for (int i = 0; i < BOX_QUANTITY; i++) {
            numberBoxes[i] = new NumberBox(texture);
            numberBoxes[i].setPosition(
                    numberBoxPlacer.getNumberBoxX(i, numberBoxes[i].getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBoxes[i].getHeight())
            );
        }
    }

    public void refresh(){
        randomNumListGenerator.clear();
        numberList.clear();
    }

    public LinkedList<Integer> getNumberList(){
        return numberList;
    }

    public void set(int index, int value){
        if (index> BOX_QUANTITY)
            index = BOX_QUANTITY;

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
        if (numberList.size()< BOX_QUANTITY)
            numberList.addAll(randomNumListGenerator.getNumbers());

        for (int i = 0; i < BOX_QUANTITY; i++)
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
        for (int i = 0; i < BOX_QUANTITY; i++) {
            if (numbers[i] > 0)
                numberBoxes[i].draw(numbers[i], batch);
        }
    }

    public void dispose() {
        for (NumberBox numberBox : numberBoxes)
            numberBox.dispose();
    }
}

class NumberBoxPlacer{
    private final float initX = GRID_X * 9.5f;
    private final float initY = GRID_Y * 5;
    private final float margin = GRID_X;

    public float getNumberBoxX(int i, float width) {
        float x = initX;

        for (int j = 0;j<3;j++)
            if (i%3 == j) x += (margin + width) * j;

        return x;
    }

    public float getNumberBoxY(int i, float height) {
        float y = initY;

        for(int j =0; j<5; j++)
            if (i/3 == j)
                y += (margin + height) * j;

        return y;
    }
}