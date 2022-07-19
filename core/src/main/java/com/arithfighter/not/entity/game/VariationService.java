package com.arithfighter.not.entity.game;

import com.arithfighter.not.font.Font;
import com.badlogic.gdx.graphics.Texture;

class VariationService {
    private final TabooNumber tabooNumber;
    private final TransformNumber transformNumber;

    public VariationService(Font font) {
        tabooNumber = new TabooNumber(font);

        tabooNumber.setValues();

        transformNumber = new TransformNumber(font);
    }

    public TabooNumber getTabooNumber() {
        return tabooNumber;
    }

    public TransformNumber getTransformNumber() {
        return transformNumber;
    }
}
