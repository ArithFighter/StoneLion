package com.arithfighter.ccg.file;

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

    public void dispose(){
        assetManager.dispose();
    }
}

class AssetAccessor{
    private final AssetManager assetManager;

    public AssetAccessor(AssetManager assetManager){
        this.assetManager = assetManager;
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
