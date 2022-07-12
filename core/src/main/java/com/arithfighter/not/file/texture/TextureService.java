package com.arithfighter.not.file.texture;

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
            "AnimationSheets",
            "Objects"
    };

    public TextureService(AssetAccessor assetAccessor) {
        textureMap = new HashMap<>();
        AssetNameLibrary f = new AssetNameLibrary();
        String[][] ts = f.getTextureNameCollection();

        for (int i = 0;i< keys.length;i++)
            textureMap.put(keys[i], assetAccessor.getTextures(ts[i]));
    }

    public String[] getKeys() {
        return keys;
    }

    public Texture[] getTextures(String key) {
        return textureMap.get(key);
    }
}
