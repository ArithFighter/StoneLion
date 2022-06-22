package com.arithfighter.not.entity.game;

import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Texture;

class VariationService {
    private final SumMask sumMask;
    private final TabooNumber tabooNumber;
    private final TransformNumber transformNumber;

    public VariationService(Texture texture, Font font) {
        sumMask = new SumMask(texture);

        tabooNumber = new TabooNumber(font);

        tabooNumber.setValues();

        transformNumber = new TransformNumber(font);
    }

    public SumMask getSumMask() {
        return sumMask;
    }

    public TabooNumber getTabooNumber() {
        return tabooNumber;
    }

    public TransformNumber getTransformNumber() {
        return transformNumber;
    }
}
