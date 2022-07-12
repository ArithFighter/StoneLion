package com.arithfighter.not.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MyAssetProcessor {
    private final AssetManager assetManager;
    private final FileLibrary fileLibrary;
    private final AssetAccessor accessor;

    public MyAssetProcessor(){
        assetManager = new AssetManager();
        fileLibrary = new FileLibrary();
        accessor = new AssetAccessor(assetManager);
    }

    public void load(){
        loadTextures(fileLibrary.getGuiFiles());

        loadTextures(fileLibrary.getCardFiles());

        loadTextures(fileLibrary.getPanelFiles());

        loadTextures(fileLibrary.getSheetFiles());

        loadTextures(fileLibrary.getObjectFiles());

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
        for(String file : fileLibrary.getMusicFiles())
            assetManager.load(file, Music.class);
    }

    private void loadSound(){
        for(String file: fileLibrary.getSoundFiles())
            assetManager.load(file, Sound.class);
    }

    public void update(int millis){
        assetManager.update(millis);
    }

    public Texture[] getGUIs() {
        return accessor.getTextures(fileLibrary.getGuiFiles());
    }

    public Texture[] getCards(){
        return accessor.getTextures(fileLibrary.getCardFiles());
    }

    public Texture[] getPanels(){
        return accessor.getTextures(fileLibrary.getPanelFiles());
    }

    public Music[] getMusics(){
        return accessor.getMusics(fileLibrary.getMusicFiles());
    }

    public Sound[] getSounds(){
        return accessor.getSounds(fileLibrary.getSoundFiles());
    }

    public Texture[] getSpriteSheet(){
        return accessor.getTextures(fileLibrary.getSheetFiles());
    }

    public Texture[] getObject(){
        return accessor.getTextures(fileLibrary.getObjectFiles());
    }

    public void dispose(){
        assetManager.dispose();
    }
}