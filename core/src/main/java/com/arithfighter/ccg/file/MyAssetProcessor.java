package com.arithfighter.ccg.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MyAssetProcessor {
    private final AssetManager assetManager;
    private final FileLibrary fileLibrary;

    public MyAssetProcessor(){
        assetManager = new AssetManager();
        fileLibrary = new FileLibrary();
    }

    public void load(){
        loadTextures();

        loadCards();

        loadMusic();

        loadSound();

        assetManager.finishLoading();
    }

    private void loadTextures(){
        for (String file : fileLibrary.getTextureFiles())
            assetManager.load(file, Texture.class);
    }

    private void loadCards(){
        for(String file : fileLibrary.getCardFiles())
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

    public Texture[] getTextures() {
        return getTextures(fileLibrary.getTextureFiles());
    }

    public Texture[] getCards(){
        return getTextures(fileLibrary.getCardFiles());
    }

    public Music[] getMusics(){
        return getMusics(fileLibrary.getMusicFiles());
    }

    public Sound[] getSounds(){
        return getSounds(fileLibrary.getSoundFiles());
    }

    private Texture[] getTextures(String[] files){
        int length = files.length;
        Texture[] textures = new Texture[length];

        for (int i = 0; i < length; i++)
            textures[i] = assetManager.get(files[i]);

        return textures;
    }

    private Music[] getMusics(String[] files){
        int length = files.length;
        Music[] audios = new Music[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }

    private Sound[] getSounds(String[] files){
        int length = files.length;
        Sound[] audios = new Sound[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }

    public void dispose(){
        assetManager.dispose();
    }
}
