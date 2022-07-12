package com.arithfighter.not.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MyAssetProcessor {
    private final AssetManager assetManager;
    private final AssetNameLibrary assetNameLibrary;

    public MyAssetProcessor(){
        assetManager = new AssetManager();
        assetNameLibrary = new AssetNameLibrary();
    }

    public void load(){
        loadTextures(assetNameLibrary.getGuiFiles());

        loadTextures(assetNameLibrary.getCardFiles());

        loadTextures(assetNameLibrary.getPanelFiles());

        loadTextures(assetNameLibrary.getSheetFiles());

        loadTextures(assetNameLibrary.getObjectFiles());

        loadMusic();

        loadSound();

        assetManager.finishLoading();
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    private void loadTextures(String[] files){
         for (String file : files)
            assetManager.load(file, Texture.class);
    }

    private void loadMusic(){
        for(String file : assetNameLibrary.getMusicFiles())
            assetManager.load(file, Music.class);
    }

    private void loadSound(){
        for(String file: assetNameLibrary.getSoundFiles())
            assetManager.load(file, Sound.class);
    }

    public void update(int millis){
        assetManager.update(millis);
    }

    public void dispose(){
        assetManager.dispose();
    }
}