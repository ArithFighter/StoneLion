package com.arithfighter.not.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MyAssetProcessor {
    private final AssetManager assetManager;
    private final AssetNameLibrary assetNameLibrary;
    private final String[][] textureCollection;

    public MyAssetProcessor(){
        assetManager = new AssetManager();
        assetNameLibrary = new AssetNameLibrary();

        textureCollection = new String[][]{
                assetNameLibrary.getGuiFiles(),
                assetNameLibrary.getCardFiles(),
                assetNameLibrary.getPanelFiles(),
                assetNameLibrary.getSheetFiles(),
                assetNameLibrary.getObjectFiles()
        };
    }

    public void load(){
        MyAssetLoader loader = new MyAssetLoader(assetManager);

        for (String[] s:textureCollection)
            loader.loadTextures(s);

        loader.loadMusic(assetNameLibrary.getMusicFiles());

        loader.loadSound(assetNameLibrary.getSoundFiles());

        assetManager.finishLoading();
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    public void update(int millis){
        assetManager.update(millis);
    }

    public void dispose(){
        assetManager.dispose();
    }
}

class MyAssetLoader{
    private final AssetManager assetManager;

    public MyAssetLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadTextures(String[] files){
         for (String file : files)
            assetManager.load(file, Texture.class);
    }

    public void loadMusic(String[] files){
        for(String file : files)
            assetManager.load(file, Music.class);
    }

    public void loadSound(String[] files){
        for(String file: files)
            assetManager.load(file, Sound.class);
    }
}