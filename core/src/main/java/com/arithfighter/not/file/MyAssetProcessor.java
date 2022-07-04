package com.arithfighter.not.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
        loadTextures(fileLibrary.getWidgetFiles());

        loadTextures(fileLibrary.getCardFiles());

        loadTextures(fileLibrary.getPanelFiles());

        loadTextures(fileLibrary.getSheetFiles());

        loadAtlas(fileLibrary.getAtlasFiles());

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

    private void loadAtlas(String[] files){
        for (String s: files)
            assetManager.load(s, TextureAtlas.class);
    }

    public void update(int millis){
        assetManager.update(millis);
    }

    public Texture[] getWidgets() {
        return accessor.getTextures(fileLibrary.getWidgetFiles());
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

    public TextureAtlas[] getTextureAtlas(){
        return accessor.getTextureAtlas(fileLibrary.getAtlasFiles());
    }

    public void dispose(){
        assetManager.dispose();
    }
}

class AssetAccessor{
    private final AssetManager assetManager;

    public AssetAccessor(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public TextureAtlas[] getTextureAtlas(String[] files){
        int length = files.length;
        TextureAtlas[] array = new TextureAtlas[length];

        for (int i = 0; i < length; i++)
            array[i] = assetManager.get(files[i]);

        return array;
    }

    public Texture[] getTextures(String[] files){
        int length = files.length;
        Texture[] array = new Texture[length];

        for (int i = 0; i < length; i++)
            array[i] = assetManager.get(files[i]);

        return array;
    }

    public Music[] getMusics(String[] files){
        int length = files.length;
        Music[] audios = new Music[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }

    public Sound[] getSounds(String[] files){
        int length = files.length;
        Sound[] audios = new Sound[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }
}
