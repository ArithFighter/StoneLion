package com.arithfighter.not.entity.game;

import com.arithfighter.not.entity.numberbox.NumberBoxEntity;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.system.RandomNumProducer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

class TransformNumber {
    private final Font font;
    private Point point;
    private int value;
    private int numberBoxIndex = -1;

    public TransformNumber(Font font) {
        this.font = font;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void init() {
        value = 0;
    }

    public void setValue(NumberBoxEntity numberBoxEntity) {
        RandomNumProducer rnp = new RandomNumProducer(numberBoxEntity.getMaxQuantity() - 1, 0);

        try {
            if (value == 0)
                value = numberBoxEntity.getNumberBoxValue(rnp.getRandomNum());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void transform(NumberBoxEntity numberBoxEntity) {
        NumberBoxPicker nbp = new NumberBoxPicker(numberBoxEntity);
        int result = 17;
        try {
            numberBoxIndex = nbp.getRandomNonZeroValueIndex();
            numberBoxEntity.set(numberBoxIndex, result);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public int getNumberBoxIndex() {
        return numberBoxIndex;
    }

    public boolean isNumberMatched(int sum) {
        return value == sum && sum > 0;
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, "Transformer: " + value, point.getX(), point.getY());
    }
}

class NumberBoxPicker {
    private final NumberBoxEntity numberBoxEntity;

    public NumberBoxPicker(NumberBoxEntity numberBoxEntity) {
        this.numberBoxEntity = numberBoxEntity;
    }

    public int getRandomNonZeroValueIndex() {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberBoxEntity.getMaxQuantity(); i++) {
            if (numberBoxEntity.getNumberBoxValue(i) > 0)
                indexList.add(i);
        }
        RandomNumProducer rnp = new RandomNumProducer(indexList.size() - 1, 0);
        int indexPick = rnp.getRandomNum();

        return indexList.get(indexPick);
    }
}