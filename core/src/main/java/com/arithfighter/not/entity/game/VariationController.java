package com.arithfighter.not.entity.game;

import com.arithfighter.not.entity.numberbox.NumberBoxEntity;
import com.arithfighter.not.entity.sum.SumDisplacerEntity;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class VariationController {
    private final VariationService variationService;
    private final SumDisplacerEntity sumDisplacerEntity;
    private NumberBoxEntity numberBoxEntity;
    private int sum;

    public VariationController(Font font, SumDisplacerEntity sumDisplacerEntity) {
        variationService = new VariationService(font);

        this.sumDisplacerEntity = sumDisplacerEntity;

        variationService.getTabooNumber().setPoint(new Point(300, 700));

        variationService.getTransformNumber().setPoint(new Point(50, 700));
    }

    public void init() {
        variationService.getTabooNumber().setValues();
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setNumberBoxEntity(NumberBoxEntity numberBoxEntity) {
        this.numberBoxEntity = numberBoxEntity;
    }

    public void changeGameVariation(GameVariation gameVariation, SpriteBatch batch) {
        switch (gameVariation) {
            case FOG:
                sumDisplacerEntity.getSumDisplacer().setDisable();
                break;
            case TABOO:
                updateTabooNumber();
                variationService.getTabooNumber().draw(batch);
                break;
            case TRANSFORM:
                variationService.getTransformNumber().draw(batch);
                updateTransformNumber();
                break;
        }
    }

    private void updateTransformNumber() {
        TransformNumber transformNumber = variationService.getTransformNumber();

        transformNumber.setValue(numberBoxEntity);

        if (transformNumber.isNumberMatched(sum)) {
            transformNumber.transform(numberBoxEntity);

            transformNumber.init();

            numberBoxEntity.setMarkerAnimationIndex(transformNumber.getNumberBoxIndex());
        }
    }

    private void updateTabooNumber() {
        TabooNumber tabooNumber = variationService.getTabooNumber();
        tabooNumber.update(sum);
        if (tabooNumber.isViolatingTaboos()) {
            doWhenViolatingTaboos();
        }
    }

    public void doWhenViolatingTaboos() {

    }
}
