package com.arithfighter.not.texture;

import com.arithfighter.not.file.AssetAccessor;
import com.arithfighter.not.file.AssetNameLibrary;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureService {
    private final Map<String, Texture[]> textureMap;
    private final String[] keys = {
            "GUIs",
            "Cards",
            "Panels",
            "Animations",
            "Objects"
    };

    public TextureService(AssetAccessor assetAccessor) {
        textureMap = new HashMap<>();

        AssetNameLibrary f = new AssetNameLibrary();

        textureMap.put(keys[0], assetAccessor.getTextures(f.getGuiFiles()));
        textureMap.put(keys[1], assetAccessor.getTextures(f.getCardFiles()));
        textureMap.put(keys[2], assetAccessor.getTextures(f.getPanelFiles()));
        textureMap.put(keys[3], assetAccessor.getTextures(f.getSheetFiles()));
        textureMap.put(keys[4], assetAccessor.getTextures(f.getObjectFiles()));
    }

    public String[] getKeys() {
        return keys;
    }

    public Texture[] getTextures(String key) {
        return textureMap.get(key);
    }
}
