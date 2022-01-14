package com.arithfighter.ccg;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class CounterAssetsManager {
    public void loadTexture(AssetManager assetManager, String[] textureFiles){
        for (String textureFile : textureFiles) {
            assetManager.load(textureFile, Texture.class);
        }
    }
}
