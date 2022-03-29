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
        loadWidgets();

        loadCards();

        loadPanels();

        loadMusic();

        loadSound();

        assetManager.finishLoading();
    }

    private void loadWidgets(){
        for (String file : fileLibrary.getWidgetFiles())
            assetManager.load(file, Texture.class);
    }

    private void loadCards(){
        for(String file : fileLibrary.getCardFiles())
            assetManager.load(file, Texture.class);
    }

    private void loadPanels(){
        for(String file : fileLibrary.getPanelFiles())
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

    public Texture[] getWidgets() {
        return getTextures(fileLibrary.getWidgetFiles());
    }

    public Texture[] getCards(){
        return getTextures(fileLibrary.getCardFiles());
    }

    public Texture[] getPanels(){
        return getTextures(fileLibrary.getPanelFiles());
    }

    public Music[] getMusics(){
        return getMusics(fileLibrary.getMusicFiles());
    }

    public Sound[] getSounds(){
        return getSounds(fileLibrary.getSoundFiles());
    }

    private Texture[] getTextures(String[] files){
        int length = files.length;
        Texture[] array = new Texture[length];

        for (int i = 0; i < length; i++)
            array[i] = assetManager.get(files[i]);

        return array;
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
