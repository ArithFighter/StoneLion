package com.arithfighter.ccg;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class CounterAssetsManager {
    public void loadCard(AssetManager assetManager, String[] cardFiles){
        for (String cardFile : cardFiles) {
            assetManager.load(cardFile, Texture.class);
        }
    }
}
