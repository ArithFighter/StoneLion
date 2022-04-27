package com.arithfighter.not;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private final Map<String, Texture[]> textureMap;
    private final String[] keys = {
            "Widgets",
            "Cards",
            "Panels",
            "SpriteSheets"
    };

    public TextureManager() {
        textureMap = new HashMap<>();
    }

    public String[] getKeys() {
        return keys;
    }

    public void put(String key, Texture[] textures) {
        textureMap.put(key, textures);
    }

    public Texture[] getTextures(String key) {
        return textureMap.get(key);
    }
}
