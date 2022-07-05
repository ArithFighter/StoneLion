package com.arithfighter.not;

import com.arithfighter.not.file.MyAssetProcessor;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureService {
    private final Map<String, Texture[]> textureMap;
    private final String[] keys = {
            "Widgets",
            "Cards",
            "Panels",
            "SpriteSheets",
            "object"
    };

    public TextureService(MyAssetProcessor assetProcessor) {
        textureMap = new HashMap<>();

        textureMap.put(keys[0], assetProcessor.getGUIs());
        textureMap.put(keys[1], assetProcessor.getCards());
        textureMap.put(keys[2], assetProcessor.getPanels());
        textureMap.put(keys[3], assetProcessor.getSpriteSheet());
        textureMap.put(keys[4], assetProcessor.getObject());
    }

    public String[] getKeys() {
        return keys;
    }

    public Texture[] getTextures(String key) {
        return textureMap.get(key);
    }
}
