package com.arithfighter.ccg;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class CounterAssetsManager {
    public void loadCard(AssetManager assetManager, String[] cardFiles){
        for (String cardFile : cardFiles) {
            assetManager.load(cardFile, Texture.class);
        }
    }

    public void getCard(AssetManager assetManager, String[] cardFiles, Texture[] cards){
        for (int i = 0; i< cardFiles.length;i++){
            cards[i] = assetManager.get(cardFiles[i], Texture.class);
        }
    }
}
