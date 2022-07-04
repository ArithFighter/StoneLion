package com.arithfighter.not;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureAtlasService {
    private final TextureRegion button;

    public TextureAtlasService(TextureAtlas atlas){
        button = atlas.findRegion("Button");
    }

    public TextureRegion getButton() {
        return button;
    }
}
